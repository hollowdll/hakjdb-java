package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.EchoServiceGrpc;
import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GrpcClient implements EchoRPC {
    private final ManagedChannel channel;
    private final EchoServiceGrpc.EchoServiceBlockingStub echoStub;
    // TODO: other stubs

    public GrpcClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.echoStub = EchoServiceGrpc.newBlockingStub(channel);
    }

    public Echo.UnaryEchoResponse unaryEcho(String message, int timeoutSeconds) {
        Echo.UnaryEchoRequest request = Echo.UnaryEchoRequest.newBuilder().setMsg(message).build();
        return echoStub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS)
                .unaryEcho(request);
    }
}
