package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.*;

public class ManagedChannelFactory {
  public static ManagedChannel createInsecureChannel(String host, int port) {
    return Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create())
        .build();
  }

  public static ManagedChannel createSecureChannel(
      String host, int port, ChannelCredentials credentials) {
    return Grpc.newChannelBuilderForAddress(host, port, credentials).build();
  }
}
