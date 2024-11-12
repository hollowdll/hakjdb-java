package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;

public interface Connection {
  /**
   * Connect to the server.
   *
   * @throws ConnectionException
   */
  void connect() throws ConnectionException;

  /**
   * Disconnect from the server.
   *
   * @throws ConnectionException
   */
  void disconnect() throws ConnectionException;
}
