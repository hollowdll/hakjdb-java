package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;

public interface EchoRPC {
     Echo.UnaryEchoResponse unaryEcho(String message, int timeoutSeconds);
}
