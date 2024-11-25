package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.authpb.Auth;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBAuthException;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;
import com.github.hakjdb.hakjdbjava.util.GrpcUtils;
import com.google.protobuf.ByteString;
import io.grpc.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
  private final ManagedChannel channel;
  private final EchoGrpcClient echoClient;
  private final StringKeyValueGrpcClient stringKeyValueClient;
  private final AuthGrpcClient authClient;
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
    this.echoClient = new EchoGrpcClient(interceptedChannel);
    this.stringKeyValueClient = new StringKeyValueGrpcClient(interceptedChannel);
    this.authClient = new AuthGrpcClient(interceptedChannel);
    this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
    this.password = config.getPassword();

    try {
      if (config.isUsePassword()) processAuth();
    } catch (HakjDBAuthException e) {
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
      EchoGrpcClient echoClient,
      StringKeyValueGrpcClient stringKeyValueClient,
      AuthGrpcClient authClient) {
    this.channel = channel;
    this.requestMetadata = requestMetadata;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
    this.password = password;
    this.echoClient = echoClient;
    this.stringKeyValueClient = stringKeyValueClient;
    this.authClient = authClient;
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
        throw new HakjDBConnectionException("Could not establish mTLS connection with CA cert, client cert and client key", e);
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

  /**
   * Calls the Authenticate RPC handler.
   *
   * @param password Server password
   * @return JWT token
   */
  public String callAuthenticate(String password) {
    Auth.AuthenticateRequest request =
        Auth.AuthenticateRequest.newBuilder().setPassword(password).build();
    Auth.AuthenticateResponse response = authClient.authenticate(request, requestTimeoutSeconds);
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
      response = echoClient.unaryEcho(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = echoClient.unaryEcho(request, requestTimeoutSeconds);
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
      stringKeyValueClient.setString(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        stringKeyValueClient.setString(request, requestTimeoutSeconds);
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
      response = stringKeyValueClient.getString(request, requestTimeoutSeconds);
    } catch (StatusRuntimeException e) {
      if (GrpcUtils.isUnauthenticated(e)) {
        processAuth();
        response = stringKeyValueClient.getString(request, requestTimeoutSeconds);
      } else {
        throw e;
      }
    }
    return response.getOk() ? response.getValue().toStringUtf8() : null;
  }
}
