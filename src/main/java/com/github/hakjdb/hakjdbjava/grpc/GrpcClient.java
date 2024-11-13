package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.authpb.Auth;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv;
import com.google.protobuf.ByteString;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

public class GrpcClient {
  private final ManagedChannel channel;
  private final EchoGrpcClient echoClient;
  private final StringKeyValueGrpcClient stringKeyValueClient;
  private final AuthGrpcClient authClient;
  private final GrpcRequestMetadata requestMetadata;
  private final int requestTimeoutSeconds;

  public GrpcClient(String host, int port, ClientConfig config) {
    this.channel = ManagedChannelFactory.createInsecureChannel(host, port);
    this.requestMetadata = new GrpcRequestMetadata(config);
    HeaderClientInterceptor interceptor =
        new HeaderClientInterceptor(requestMetadata.getMetadata());
    Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);
    this.echoClient = new EchoGrpcClient(interceptedChannel);
    this.stringKeyValueClient = new StringKeyValueGrpcClient(interceptedChannel);
    this.authClient = new AuthGrpcClient(interceptedChannel);
    this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
  }

  public GrpcClient(
      ManagedChannel channel,
      GrpcRequestMetadata requestMetadata,
      int requestTimeoutSeconds,
      EchoGrpcClient echoClient,
      StringKeyValueGrpcClient stringKeyValueClient,
      AuthGrpcClient authClient) {
    this.channel = channel;
    this.requestMetadata = requestMetadata;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
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

  public String callAuthenticate(String password) {
    Auth.AuthenticateRequest request = Auth.AuthenticateRequest.newBuilder().setPassword(password).build();
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
    Echo.UnaryEchoResponse response = echoClient.unaryEcho(request, requestTimeoutSeconds);
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
    stringKeyValueClient.setString(request, requestTimeoutSeconds);
  }

  /**
   * Calls the GetString RPC handler.
   *
   * @param key Key to use
   * @return Retrieved value
   */
  public String callGetString(String key) {
    StringKv.GetStringRequest request = StringKv.GetStringRequest.newBuilder().setKey(key).build();
    StringKv.GetStringResponse response =
        stringKeyValueClient.getString(request, requestTimeoutSeconds);
    return response.getValue().toStringUtf8();
  }
}
