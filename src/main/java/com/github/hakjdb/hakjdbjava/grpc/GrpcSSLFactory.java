package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.SslContext;

import javax.net.ssl.SSLException;
import java.io.File;

public class GrpcSSLFactory {
  public static SslContext createSSLContext(String caCertPath) throws SSLException {
    return GrpcSslContexts.forClient().trustManager(new File(caCertPath)).build();
  }

  public static SslContext createClientCertAuthSSLContext(
      String caCertPath, String clientCertPath, String clientKeyPath) throws SSLException {
    return GrpcSslContexts.forClient()
        .trustManager(new File(caCertPath))
        .keyManager(new File(clientCertPath), new File(clientKeyPath))
        .build();
  }
}
