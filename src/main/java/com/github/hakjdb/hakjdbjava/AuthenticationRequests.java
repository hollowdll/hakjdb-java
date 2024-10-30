package com.github.hakjdb.hakjdbjava;

public interface AuthenticationRequests {
    /**
     * Authenticates the client with the server password.
     * Returns a JWT token if authentication was successful.
     *
     * @param password The server password
     * @return JWT token
     */
    String authenticate(String password);
}
