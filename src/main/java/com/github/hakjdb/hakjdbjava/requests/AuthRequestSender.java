package com.github.hakjdb.hakjdbjava.requests;

public interface AuthRequestSender {
  /**
   * Sends authenticate request.
   *
   * @param password The server password
   * @return JWT token
   */
  String sendRequestAuthenticate(String password);
}
