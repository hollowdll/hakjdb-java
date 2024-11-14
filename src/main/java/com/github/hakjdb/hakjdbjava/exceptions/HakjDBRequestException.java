package com.github.hakjdb.hakjdbjava.exceptions;

public class HakjDBRequestException extends RuntimeException {
  public HakjDBRequestException(String message) {
    super(message);
  }

  public HakjDBRequestException(Throwable cause) {
    super(cause);
  }

  public HakjDBRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
