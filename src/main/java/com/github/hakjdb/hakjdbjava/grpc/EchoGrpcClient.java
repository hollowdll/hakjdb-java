package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoResponse;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoRequest;

public interface EchoGrpcClient {
  /**
   * Calls the RPC UnaryEcho.
   *
   * @param request Request data
   * @param timeoutSeconds Request timeout in seconds
   * @return RPC response
   */
  UnaryEchoResponse unaryEcho(UnaryEchoRequest request, int timeoutSeconds);
}
