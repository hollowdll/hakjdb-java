package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;
import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;
import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;
import com.github.hakjdb.hakjdbjava.requests.AuthRequests;
import com.github.hakjdb.hakjdbjava.requests.DatabaseRequests;
import com.github.hakjdb.hakjdbjava.requests.EchoRequests;
import com.github.hakjdb.hakjdbjava.requests.StringKeyValueRequests;

import java.util.Set;

public class HakjDB
    implements AuthRequests, EchoRequests, StringKeyValueRequests, DatabaseRequests {
  private final GrpcConnection connection;

  public HakjDB() {
    try {
      this.connection = new GrpcConnection();
      this.connection.connect();
    } catch (HakjDBConnectionException e) {
      disconnectNow();
      throw e;
    }
  }

  public HakjDB(String host, int port) {
    try {
      this.connection = new GrpcConnection(host, port);
      this.connection.connect();
    } catch (HakjDBConnectionException e) {
      disconnectNow();
      throw e;
    }
  }

  public HakjDB(String host, int port, ClientConfig config) {
    try {
      this.connection = new GrpcConnection(host, port, config);
      this.connection.connect();
    } catch (HakjDBConnectionException e) {
      disconnectNow();
      throw e;
    }
  }

  /** Disconnect from the HakjDB server. This method closes the connection gracefully. */
  public void disconnect() {
    if (connection != null) {
      connection.disconnect();
    }
  }

  /**
   * Disconnect from the HakjDB server. This method closes the connection immediately. If there are
   * requests being processed when this is called, they may get interrupted.
   */
  public void disconnectNow() {
    if (connection != null) {
      connection.disconnectNow();
    }
  }

  @Override
  public String authenticate(String password) {
    return connection.sendRequestAuthenticate(password);
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

  @Override
  public String createDatabase(String dbName, String dbDescription) {
    return connection.sendRequestCreateDatabase(dbName, dbDescription);
  }

  @Override
  public Set<String> getDatabases() {
    return connection.sendRequestGetDatabases();
  }

  @Override
  public String getDatabaseInfo(String dbName) {
    return connection.sendRequestGetDatabaseInfo(dbName);
  }

  @Override
  public String deleteDatabase(String dbName) {
    return connection.sendRequestDeleteDatabase(dbName);
  }

  @Override
  public String changeDatabase(String dbName, ChangeDatabaseOptions options) {
    return connection.sendRequestChangeDatabase(dbName, options);
  }
}
