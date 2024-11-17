package com.github.hakjdb.hakjdbjava.exceptions;

public class HakjDBAuthException extends RuntimeException {
  public HakjDBAuthException(String message) {
    super(message);
  }

  public HakjDBAuthException(Throwable cause) {
    super(cause);
  }

  public HakjDBAuthException(String message, Throwable cause) {
    super(message, cause);
  }
}
