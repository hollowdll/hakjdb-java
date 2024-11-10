package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.SetStringResponse;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.GetStringRequest;
import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv.GetStringResponse;

public interface StringKeyValueGrpcClient {
  /**
   * Calls RPC SetString
   *
   * @param request Request data
   * @param timeoutSeconds Request timeout in seconds
   * @return RPC response
   */
  SetStringResponse setString(SetStringRequest request, int timeoutSeconds);

  /**
   * Calls RPC GetString
   *
   * @param request Request data
   * @param timeoutSeconds Request timeout in seconds
   * @return RPC response
   */
  GetStringResponse getString(GetStringRequest request, int timeoutSeconds);
}
