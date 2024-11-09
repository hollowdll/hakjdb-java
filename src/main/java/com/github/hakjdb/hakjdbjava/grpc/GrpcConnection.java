package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.ConfigDefaults;
import com.github.hakjdb.hakjdbjava.Connection;
import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;

public class GrpcConnection implements Connection {
  private final ClientConfig config;
  private final GrpcClient grpcClient;

  public GrpcConnection() {
    this.config = ClientConfig.builder().build();
    this.grpcClient =
        new DefaultGrpcClient(
            ConfigDefaults.DEFAULT_HOST, ConfigDefaults.DEFAULT_PORT, this.config);
  }

  public GrpcConnection(String host, int port) {
    this.config = ClientConfig.builder().build();
    this.grpcClient = new DefaultGrpcClient(host, port, this.config);
  }

  public GrpcConnection(String host, int port, ClientConfig config) {
    this.config = config;
    this.grpcClient = new DefaultGrpcClient(host, port, config);
  }

  public GrpcConnection(GrpcClient grpcClient) {
    this.config = ClientConfig.builder().build();
    this.grpcClient = grpcClient;
  }

  public GrpcConnection(GrpcClient grpcClient, ClientConfig config) {
    this.config = config;
    this.grpcClient = grpcClient;
  }

  public void connect() throws ConnectionException {
    try {
      sendRequestEcho("");
    } catch (Exception e) {
      throw new ConnectionException("Could not connect", e);
    }
  }

  public void disconnect() throws ConnectionException {
    try {
      grpcClient.shutdown(config.getDisconnectWaitTimeSeconds());
    } catch (Exception e) {
      throw new ConnectionException("Could not disconnect", e);
    }
  }

  public String sendRequestEcho(String message) {
    return grpcClient.callUnaryEcho(message);
  }
}