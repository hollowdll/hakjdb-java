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

  /**
   * Send echo request.
   *
   * @param message The message to send
   * @return The message that was received
   */
  String sendRequestEcho(String message);

  /**
   * Send set request.
   *
   * @param key Key to use
   * @param value Value to store
   */
  void sendRequestSet(String key, String value);

  /**
   * Send get request.
   *
   * @param key Key to use
   * @return Retrieved value
   */
  String sendRequestGet(String key);
}
