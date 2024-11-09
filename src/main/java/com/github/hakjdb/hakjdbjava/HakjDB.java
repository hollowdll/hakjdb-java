package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;

public class HakjDB implements EchoRequests {
  private final Connection connection;

  public HakjDB() {
    this.connection = new GrpcConnection();
    this.connection.connect();
  }

  public HakjDB(String host, int port) {
    this.connection = new GrpcConnection(host, port);
    this.connection.connect();
  }

  public HakjDB(String host, int port, ClientConfig config) {
    this.connection = new GrpcConnection(host, port, config);
    this.connection.connect();
  }

  public void disconnect() {
    this.connection.disconnect();
  }

  @Override
  public String echo(String message) {
    return connection.sendRequestEcho(message);
  }
}
