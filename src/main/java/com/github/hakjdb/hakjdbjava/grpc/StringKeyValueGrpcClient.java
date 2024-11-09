package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringResponse;

public interface StringKeyValueGrpcClient {
    /**
     * Calls RPC SetString
     *
     * @param request Request data
     * @param timeoutSeconds Request timeout in seconds
     * @return RPC response
     */
    SetStringResponse setString(SetStringRequest request, int timeoutSeconds);
}
