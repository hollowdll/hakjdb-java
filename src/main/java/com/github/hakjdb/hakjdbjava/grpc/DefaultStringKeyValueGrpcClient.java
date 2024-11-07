package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringResponse;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKVServiceGrpc;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

public class DefaultStringKeyValueGrpcClient implements StringKeyValueGrpcClient {
    private final StringKVServiceGrpc.StringKVServiceBlockingStub stub;

    public DefaultStringKeyValueGrpcClient(ManagedChannel channel) {
        this.stub = StringKVServiceGrpc.newBlockingStub(channel);
    }

    public SetStringResponse setString(SetStringRequest request, int timeoutSeconds, GrpcRequestMetadata metadata) {
        return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS)
                .setString(request);
    }
}
