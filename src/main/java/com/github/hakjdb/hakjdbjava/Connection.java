package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;

public interface Connection {
  /**
   * Connect to the server.
   *
   * @throws HakjDBConnectionException
   */
  void connect() throws HakjDBConnectionException;

  /**
   * Disconnect from the server gracefully.
   *
   * @throws HakjDBConnectionException
   */
  void disconnect() throws HakjDBConnectionException;

  /**
   * Disconnect from the server immediately.
   *
   * @throws HakjDBConnectionException
   */
  void disconnectNow() throws HakjDBConnectionException;
}
