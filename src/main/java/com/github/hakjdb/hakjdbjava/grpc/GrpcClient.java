package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.authpb.Auth;
import com.github.hakjdb.hakjdbjava.api.v1.dbpb.Db;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBAuthException;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;
import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;
import com.github.hakjdb.hakjdbjava.util.GrpcUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
  private final ManagedChannel channel;
  private final EchoGrpcClient echoGrpcClient;
  private final StringKeyValueGrpcClient stringKeyValueGrpcClient;
  private final AuthGrpcClient authGrpcClient;
  private final DatabaseGrpcClient databaseGrpcClient;
  private final GrpcRequestMetadata requestMetadata;
  private final int requestTimeoutSeconds;
  private final String password;

  public GrpcClient(String host, int port, ClientConfig config) {
    if (config.isUseTLS()) {
      ChannelCredentials channelCredentials = determineChannelCredentials(config);
      this.channel = ManagedChannelFactory.createSecureChannel(host, port, channelCredentials);
    } else {
      this.channel = ManagedChannelFactory.createInsecureChannel(host, port);
    }

    this.requestMetadata = new GrpcRequestMetadata(config);
    HeaderClientInterceptor interceptor =
        new HeaderClientInterceptor(requestMetadata.getMetadata());
    Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);
    this.echoGrpcClient = new EchoGrpcClient(interceptedChannel);
    this.stringKeyValueGrpcClient = new StringKeyValueGrpcClient(interceptedChannel);
    this.authGrpcClient = new AuthGrpcClient(interceptedChannel);
    this.databaseGrpcClient = new DatabaseGrpcClient(interceptedChannel);
    this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
    this.password = config.getPassword();

    try {
      if (config.isUsePassword()) processAuth();
    } catch (HakjDBAuthException e) {
      forcefulShutdown();
      throw new HakjDBConnectionException(
          "Could not authenticate the client when establishing connection: "
              + e.getCause().getMessage());
    }
  }

  public GrpcClient(
      ManagedChannel channel,
      GrpcRequestMetadata requestMetadata,
      int requestTimeoutSeconds,
      String password,
      EchoGrpcClient echoGrpcClient,
      StringKeyValueGrpcClient stringKeyValueGrpcClient,
      AuthGrpcClient authGrpcClient,
      DatabaseGrpcClient databaseGrpcClient) {
    this.channel = channel;
    this.requestMetadata = requestMetadata;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
    this.password = password;
    this.echoGrpcClient = echoGrpcClient;
    this.stringKeyValueGrpcClient = stringKeyValueGrpcClient;
    this.authGrpcClient = authGrpcClient;
    this.databaseGrpcClient = databaseGrpcClient;
  }

  public int getRequestTimeoutSeconds() {
    return requestTimeoutSeconds;
  }

  public GrpcRequestMetadata getRequestMetadata() {
    return requestMetadata;
  }

  private void processAuth() {
    try {
      String authToken = callAuthenticate(password);
      requestMetadata.setAuthToken(authToken);
    } catch (StatusRuntimeException e) {
      throw new HakjDBAuthException("Could not obtain auth token: " + e.getMessage(), e);
    }
  }

  private ChannelCredentials determineChannelCredentials(ClientConfig config) {
    if (config.isUseClientCertAuth()) {
      try {
        return GrpcChannelCredentialsFactory.createMutualTLSChannelCredentials(
            config.getTlsCACertPath(), config.getTlsClientCertPath(), config.getTlsClientKeyPath());
      } catch (IOException e) {
        throw new HakjDBConnectionException(
            "Could not establish mTLS connection with CA cert, client cert and client key", e);
      }
    } else {
      try {
        return GrpcChannelCredentialsFactory.createTLSChannelCredentials(config.getTlsCACertPath());
      } catch (IOException e) {
        throw new HakjDBConnectionException("Could not establish TLS connection with CA cert", e);
      }
    }
  }

  /**
   * Shuts down the client channel.
   *
   * @param waitTimeSeconds Time to wait in seconds
   */
  public void shutdown(long waitTimeSeconds) {
    if (channel != null && !channel.isShutdown()) {
      try {
        channel.shutdown().awaitTermination(waitTimeSeconds, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        channel.shutdownNow();
      } finally {
        if (!channel.isShutdown()) {
          channel.shutdownNow();
        }
      }
    }
  }

  /** Shuts down the client channel immediately. */
  public void forcefulShutdown() {
    if (channel != null && !channel.isShutdown()) {
      channel.shutdownNow();
    }
  }

  /**
   * Calls the Authenticate RPC handler.
   *
   * @param password Server password
   * @return JWT token
   */
  public String callAuthenticate(String password) {
    Auth.AuthenticateRequest request =
        Auth.AuthenticateRequest.newBuilder().setPassword(password).build();
    Auth.AuthenticateResponse response =
        authGrpcClient.authenticate(request, requestTimeoutSeconds);
    return response.getAuthToken();
  }

  /**
   * Calls the UnaryEcho RPC handler.
   *
   * @param message Message to send
   * @return Received message
   */
  public String callUnaryEcho(String message) {
    Echo.UnaryEchoRequest request = Echo.UnaryEchoRequest.newBuilder().setMsg(message).build();
    Echo.UnaryEchoResponse response;
    try {
      response = echoGrpcClient.unaryEcho(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = echoGrpcClient.unaryEcho(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return response.getMsg();
  }

  /**
   * Calls the SetString RPC handler.
   *
   * @param key Key to use
   * @param value Value to store
   */
  public void callSetString(String key, String value) {
    StringKv.SetStringRequest request =
        StringKv.SetStringRequest.newBuilder()
            .setKey(key)
            .setValue(ByteString.copyFromUtf8(value))
            .build();
    try {
      stringKeyValueGrpcClient.setString(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        stringKeyValueGrpcClient.setString(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
  }

  /**
   * Calls the GetString RPC handler.
   *
   * @param key Key to use
   * @return Retrieved value or null if key doesn't exist.
   */
  public String callGetString(String key) {
    StringKv.GetStringRequest request = StringKv.GetStringRequest.newBuilder().setKey(key).build();
    StringKv.GetStringResponse response;
    try {
      response = stringKeyValueGrpcClient.getString(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = stringKeyValueGrpcClient.getString(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return response.getOk() ? response.getValue().toStringUtf8() : null;
  }

  /**
   * Calls the CreateDB RPC handler.
   *
   * @param dbName Name of the database
   * @param dbDescription Description of the database
   * @return Name of the created database
   */
  public String callCreateDatabase(String dbName, String dbDescription) {
    Db.CreateDBRequest request =
        Db.CreateDBRequest.newBuilder().setDbName(dbName).setDescription(dbDescription).build();
    Db.CreateDBResponse response;
    try {
      response = databaseGrpcClient.createDatabase(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = databaseGrpcClient.createDatabase(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return response.getDbName();
  }

  /**
   * Calls the GetAllDBs RPC handler.
   *
   * @return Database names.
   */
  public Set<String> callGetDatabases() {
    Db.GetAllDBsRequest request = Db.GetAllDBsRequest.newBuilder().build();
    Db.GetAllDBsResponse response;
    try {
      response = databaseGrpcClient.getDatabases(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = databaseGrpcClient.getDatabases(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return new HashSet<>(response.getDbNamesList());
  }

  /**
   * Calls the GetDBInfo RPC handler.
   *
   * @param dbName Name of the database
   * @return JSON string of database information
   */
  public String callGetDatabaseInfo(String dbName) throws InvalidProtocolBufferException {
    Db.GetDBInfoRequest request = Db.GetDBInfoRequest.newBuilder().setDbName(dbName).build();
    Db.GetDBInfoResponse response;
    try {
      response = databaseGrpcClient.getDatabaseInfo(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = databaseGrpcClient.getDatabaseInfo(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return JsonFormat.printer().print(response);
  }

  /**
   * Calls the CreateDB RPC handler.
   *
   * @param dbName Name of the database
   * @return Name of the deleted database
   */
  public String callDeleteDatabase(String dbName) {
    Db.DeleteDBRequest request = Db.DeleteDBRequest.newBuilder().setDbName(dbName).build();
    Db.DeleteDBResponse response;
    try {
      response = databaseGrpcClient.deleteDatabase(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = databaseGrpcClient.deleteDatabase(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return response.getDbName();
  }

  /**
   * Calls the CreateDB RPC handler.
   *
   * @param dbName Name of the database
   * @param options Options of what to change
   * @return Name of the changed database
   */
  public String callChangeDatabase(String dbName, ChangeDatabaseOptions options) {
    Db.ChangeDBRequest request = Db.ChangeDBRequest.newBuilder().setDbName(dbName).build();
    String newName = options.getNewName();
    String newDescription = options.getNewDescription();

    if (newName != null) {
      request = request.toBuilder().setChangeName(true).setNewName(newName).build();
    } else {
      request = request.toBuilder().setChangeName(false).setNewName("").build();
    }

    if (newDescription != null) {
      request = request.toBuilder().setChangeDescription(true).setNewDescription(newDescription).build();
    } else {
      request = request.toBuilder().setChangeDescription(false).setNewDescription("").build();
    }

    Db.ChangeDBResponse response;
    try {
      response = databaseGrpcClient.changeDatabase(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = databaseGrpcClient.changeDatabase(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return response.getDbName();
  }
}
