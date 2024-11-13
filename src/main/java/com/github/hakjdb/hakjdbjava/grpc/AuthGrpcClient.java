package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.authpb.AuthServiceGrpc;
import com.github.hakjdb.hakjdbjava.api.v1.authpb.Auth.AuthenticateRequest;
import com.github.hakjdb.hakjdbjava.api.v1.authpb.Auth.AuthenticateResponse;
import io.grpc.Channel;

import java.util.concurrent.TimeUnit;

public class AuthGrpcClient {
  private final AuthServiceGrpc.AuthServiceBlockingStub stub;

  public AuthGrpcClient(Channel channel) {
    this.stub = AuthServiceGrpc.newBlockingStub(channel);
  }

  /**
   * Calls the RPC Authenticate.
   *
   * @param request Request data
   * @param timeoutSeconds Request timeout in seconds
   * @return RPC response
   */
  public AuthenticateResponse authenticate(AuthenticateRequest request, int timeoutSeconds) {
    return stub.withDeadlineAfter(timeoutSeconds, TimeUnit.SECONDS).authenticate(request);
  }
}
