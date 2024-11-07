package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoResponse;

public interface EchoGrpcClient {
    /**
     * Calls the RPC UnaryEcho.
     *
     * @param message
     * @param timeoutSeconds
     * @param metadata
     * @return RPC response
     */
    UnaryEchoResponse unaryEcho(String message, int timeoutSeconds, GrpcRequestMetadata metadata);
}
