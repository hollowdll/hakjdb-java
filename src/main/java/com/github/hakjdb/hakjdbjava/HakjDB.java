package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;
import com.github.hakjdb.hakjdbjava.requests.EchoRequests;
import com.github.hakjdb.hakjdbjava.requests.StringKeyValueRequests;

public class HakjDB implements EchoRequests, StringKeyValueRequests {
  private final GrpcConnection connection;

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

  @Override
  public void set(String key, String value) {
    connection.sendRequestSet(key, value);
  }

  @Override
  public String get(String key) {
    return connection.sendRequestGet(key);
  }
}
