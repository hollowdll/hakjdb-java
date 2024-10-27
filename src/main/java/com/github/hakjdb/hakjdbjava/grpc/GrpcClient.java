package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.EchoServiceGrpc;
import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    private final ManagedChannel channel;
    private final EchoServiceGrpc.EchoServiceBlockingStub echoStub;
    // TODO: other stubs

    public GrpcClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.echoStub = EchoServiceGrpc.newBlockingStub(channel);
    }
}
