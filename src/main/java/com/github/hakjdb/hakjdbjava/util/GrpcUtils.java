package com.github.hakjdb.hakjdbjava.util;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public final class GrpcUtils {
  public static boolean isUnauthenticated(StatusRuntimeException e) {
    return e.getStatus().getCode() == Status.Code.UNAUTHENTICATED;
  }
}
