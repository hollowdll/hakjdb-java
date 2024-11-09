package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.EchoServiceGrpc;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoResponse;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo.UnaryEchoRequest;
import io.grpc.Channel;

import java.util.concurrent.TimeUnit;

public class DefaultEchoGrpcClient implements EchoGrpcClient {
    private final EchoServiceGrpc.EchoServiceBlockingStub stub;

    public DefaultEchoGrpcClient(Channel channel) {
        this.stub = EchoServiceGrpc.newBlockingStub(channel);
    }

    public UnaryEchoResponse unaryEcho(UnaryEchoRequest request, int timeoutSeconds) {
        return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS)
                .unaryEcho(request);
    }
}
