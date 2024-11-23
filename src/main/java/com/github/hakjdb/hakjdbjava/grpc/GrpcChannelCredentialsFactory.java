package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.ChannelCredentials;
import io.grpc.TlsChannelCredentials;

import java.io.File;
import java.io.IOException;

public class GrpcChannelCredentialsFactory {
  public static ChannelCredentials createTLSChannelCredentials(String caCertPath)
      throws IOException {
    return TlsChannelCredentials.newBuilder().trustManager(new File(caCertPath)).build();
  }

  public static ChannelCredentials createMutualTLSChannelCredentials(
      String caCertPath, String clientCertPath, String clientKeyPath) throws IOException {
    return TlsChannelCredentials.newBuilder()
        .trustManager(new File(caCertPath))
        .keyManager(new File(clientCertPath), new File(clientKeyPath))
        .build();
  }
}
