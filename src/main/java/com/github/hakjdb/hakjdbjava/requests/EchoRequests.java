package com.github.hakjdb.hakjdbjava.requests;

public interface EchoRequests {
  /**
   * Sends a message to the server and returns the same message back. Useful for checking if the
   * connection is working.
   *
   * @param message The message to send
   * @return The message that was received
   */
  String echo(String message);
}
