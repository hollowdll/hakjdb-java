package com.github.hakjdb.hakjdbjava.unit.client;

import com.github.hakjdb.hakjdbjava.api.v1.authpb.Auth;

import com.github.hakjdb.hakjdbjava.grpc.AuthGrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthGrpcClientTest {
  @Mock private AuthGrpcClient authGrpcClient;
  private GrpcClient grpcClient;

  @BeforeEach
  void setup() {
    grpcClient = new GrpcClient(null, null, 0, "", null, null, authGrpcClient, null);
  }

  @Test
  public void callAuthenticate() {
    String password = "pass123";
    String authToken = "asd-123-fgh-456";
    Auth.AuthenticateRequest request =
        Auth.AuthenticateRequest.newBuilder().setPassword(password).build();
    Auth.AuthenticateResponse response =
        Auth.AuthenticateResponse.newBuilder().setAuthToken(authToken).build();

    when(authGrpcClient.authenticate(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    String result = grpcClient.callAuthenticate(password);
    assertEquals(authToken, result);
    verify(authGrpcClient).authenticate(request, grpcClient.getRequestTimeoutSeconds());
  }
}
