package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringResponse;

import java.nio.ByteBuffer;

public interface StringKeyValueGrpcClient {
    /**
     * Calls the RPC SetString
     *
     * @param request Request data
     * @param timeoutSeconds Request timeout in seconds
     * @param metadata Request metadata
     * @return RPC response
     */
    SetStringResponse setString(SetStringRequest request, int timeoutSeconds, GrpcRequestMetadata metadata);
}
