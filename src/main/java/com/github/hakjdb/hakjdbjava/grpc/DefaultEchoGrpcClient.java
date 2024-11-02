package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.EchoServiceGrpc;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

public class DefaultEchoGrpcClient implements EchoGrpcClient {
    private final EchoServiceGrpc.EchoServiceBlockingStub echoStub;

    public DefaultEchoGrpcClient(ManagedChannel channel) {
        this.echoStub = EchoServiceGrpc.newBlockingStub(channel);
    }

    public Echo.UnaryEchoResponse unaryEcho(String message, int timeoutSeconds, GrpcRequestMetadata metadata) {
        Echo.UnaryEchoRequest request = Echo.UnaryEchoRequest.newBuilder().setMsg(message).build();
        return echoStub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS)
                .unaryEcho(request);
    }
}
