package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;

public class ManagedChannelFactory {
  public static ManagedChannel createInsecureChannel(String host, int port) {
    return ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
  }

  public static ManagedChannel createSecureChannel(String host, int port, SslContext sslContext) {
    return NettyChannelBuilder.forAddress(host, port).sslContext(sslContext).build();
  }
}
