package com.github.hakjdb.hakjdbjava.requests;

public interface AuthRequestSender {
  /**
   * Sends authenticate request.
   *
   * @param password Server password
   * @return JWT token
   */
  String sendRequestAuthenticate(String password);
}
