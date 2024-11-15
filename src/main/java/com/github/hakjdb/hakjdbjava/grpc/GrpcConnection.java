package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.ConfigDefaults;
import com.github.hakjdb.hakjdbjava.Connection;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBRequestException;
import com.github.hakjdb.hakjdbjava.requests.AuthRequestSender;
import com.github.hakjdb.hakjdbjava.requests.EchoRequestSender;
import com.github.hakjdb.hakjdbjava.requests.StringKeyValueRequestSender;
import io.grpc.StatusRuntimeException;

public class GrpcConnection
    implements Connection, AuthRequestSender, EchoRequestSender, StringKeyValueRequestSender {
  private final ClientConfig config;
  private final GrpcClient grpcClient;

  public GrpcConnection() {
    this.config = ClientConfig.builder().build();
    this.grpcClient =
        new GrpcClient(ConfigDefaults.DEFAULT_HOST, ConfigDefaults.DEFAULT_PORT, this.config);
  }

  public GrpcConnection(String host, int port) {
    this.config = ClientConfig.builder().build();
    this.grpcClient = new GrpcClient(host, port, this.config);
  }

  public GrpcConnection(String host, int port, ClientConfig config) {
    this.config = config;
    this.grpcClient = new GrpcClient(host, port, config);
  }

  public GrpcConnection(GrpcClient grpcClient) {
    this.config = ClientConfig.builder().build();
    this.grpcClient = grpcClient;
  }

  public GrpcConnection(GrpcClient grpcClient, ClientConfig config) {
    this.config = config;
    this.grpcClient = grpcClient;
  }

  @Override
  public void connect() throws HakjDBConnectionException {
    try {
      sendRequestEcho("");
    } catch (Exception e) {
      throw new HakjDBConnectionException("Could not connect", e);
    }
  }

  @Override
  public void disconnect() throws HakjDBConnectionException {
    try {
      grpcClient.shutdown(config.getDisconnectWaitTimeSeconds());
    } catch (Exception e) {
      throw new HakjDBConnectionException("Could not disconnect", e);
    }
  }

  @Override
  public String sendRequestAuthenticate(String password) {
    try {
      return grpcClient.callAuthenticate(password);
    } catch (Exception e) {
      throw new HakjDBRequestException(createRequestFailedMessage(e), e);
    }
  }

  @Override
  public String sendRequestEcho(String message) {
    try {
      return grpcClient.callUnaryEcho(message);
    } catch (Exception e) {
      throw new HakjDBRequestException(createRequestFailedMessage(e), e);
    }
  }

  @Override
  public void sendRequestSet(String key, String value) {
    try {
      grpcClient.callSetString(key, value);
    } catch (Exception e) {
      throw new HakjDBRequestException(createRequestFailedMessage(e), e);
    }

  }

  @Override
  public String sendRequestGet(String key) {
    try {
      return grpcClient.callGetString(key);
    } catch (Exception e) {
      throw new HakjDBRequestException(createRequestFailedMessage(e), e);
    }
  }

  private String createRequestFailedMessage(Exception e) {
    return "Request failed: " + e.getMessage();
  }
}
