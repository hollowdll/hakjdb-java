package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoRequest;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoResponse;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringResponse;
import com.google.protobuf.ByteString;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

public class DefaultGrpcClient implements GrpcClient {
  private final ManagedChannel channel;
  private final EchoGrpcClient echoClient;
  private final StringKeyValueGrpcClient stringKeyValueClient;
  private final GrpcRequestMetadata requestMetadata;
  private final int requestTimeoutSeconds;

  public DefaultGrpcClient(String host, int port, ClientConfig config) {
    this.channel = ManagedChannelFactory.createInsecureChannel(host, port);
    this.requestMetadata = new GrpcRequestMetadata(config);
    HeaderClientInterceptor interceptor =
        new HeaderClientInterceptor(requestMetadata.getMetadata());
    Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);
    this.echoClient = new DefaultEchoGrpcClient(interceptedChannel);
    this.stringKeyValueClient = new DefaultStringKeyValueGrpcClient(interceptedChannel);
    this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
  }

  public DefaultGrpcClient(
      ManagedChannel channel,
      GrpcRequestMetadata requestMetadata,
      int requestTimeoutSeconds,
      EchoGrpcClient echoClient,
      StringKeyValueGrpcClient stringKeyValueClient) {
    this.channel = channel;
    this.requestMetadata = requestMetadata;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
    this.echoClient = echoClient;
    this.stringKeyValueClient = stringKeyValueClient;
  }

  public int getRequestTimeoutSeconds() {
    return requestTimeoutSeconds;
  }

  public GrpcRequestMetadata getRequestMetadata() {
    return requestMetadata;
  }

  @Override
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

  @Override
  public String callUnaryEcho(String message) {
    UnaryEchoRequest request = UnaryEchoRequest.newBuilder().setMsg(message).build();
    UnaryEchoResponse response = echoClient.unaryEcho(request, requestTimeoutSeconds);
    return response.getMsg();
  }

  @Override
  public void callSetString(String key, String value) {
    SetStringRequest request =
        SetStringRequest.newBuilder().setKey(key).setValue(ByteString.copyFromUtf8(value)).build();
    SetStringResponse response = stringKeyValueClient.setString(request, requestTimeoutSeconds);
  }
}
