package com.github.hakjdb.hakjdbjava.exceptions;

public class HakjDBConnectionException extends RuntimeException {
  public HakjDBConnectionException(String message) {
    super(message);
  }

  public HakjDBConnectionException(Throwable cause) {
    super(cause);
  }

  public HakjDBConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
