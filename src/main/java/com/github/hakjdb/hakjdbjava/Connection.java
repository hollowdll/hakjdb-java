package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;
import com.github.hakjdb.hakjdbjava.grpc.DefaultGrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;

public class Connection {
  private final ClientConfig config;
  private final GrpcClient grpcClient;

  public Connection() {
    this.config = ClientConfig.builder().build();
    this.grpcClient =
        new DefaultGrpcClient(
            ConfigDefaults.DEFAULT_HOST, ConfigDefaults.DEFAULT_PORT, this.config);
  }

  public Connection(String host, int port) {
    this.config = ClientConfig.builder().build();
    this.grpcClient = new DefaultGrpcClient(host, port, this.config);
  }

  public Connection(String host, int port, ClientConfig config) {
    this.config = config;
    this.grpcClient = new DefaultGrpcClient(host, port, config);
  }

  public Connection(GrpcClient grpcClient) {
    this.config = ClientConfig.builder().build();
    this.grpcClient = grpcClient;
  }

  public Connection(GrpcClient grpcClient, ClientConfig config) {
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
