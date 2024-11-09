package com.github.hakjdb.hakjdbjava.exceptions;

public class ConnectionException extends RuntimeException {
  public ConnectionException(String message) {
    super(message);
  }

  public ConnectionException(Throwable cause) {
    super(cause);
  }

  public ConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
