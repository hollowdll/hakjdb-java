package com.github.hakjdb.hakjdbjava.requests;

public interface AuthRequests {
  /**
   * Authenticates the client with the server password. Returns a JWT token if authentication was
   * successful.
   *
   * @param password The server password
   * @return JWT token
   */
  String authenticate(String password);
}
