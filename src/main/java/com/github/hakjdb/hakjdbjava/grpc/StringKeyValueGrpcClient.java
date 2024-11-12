package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringResponse;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.GetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.GetStringResponse;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKVServiceGrpc;
import io.grpc.Channel;

import java.util.concurrent.TimeUnit;

public class StringKeyValueGrpcClient {
  private final StringKVServiceGrpc.StringKVServiceBlockingStub stub;

  public StringKeyValueGrpcClient(Channel channel) {
    this.stub = StringKVServiceGrpc.newBlockingStub(channel);
  }

  /**
   * Calls RPC SetString
   *
   * @param request Request data
   * @param timeoutSeconds Request timeout in seconds
   * @return RPC response
   */
  public SetStringResponse setString(SetStringRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).setString(request);
  }

  /**
   * Calls RPC GetString
   *
   * @param request Request data
   * @param timeoutSeconds Request timeout in seconds
   * @return RPC response
   */
  public GetStringResponse getString(GetStringRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).getString(request);
  }
}
