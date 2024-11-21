package com.github.hakjdb.hakjdbjava.unit.connection;

import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthGrpcConnectionTest {
  @Mock private GrpcClient grpcClient;
  private GrpcConnection connection;

  @BeforeEach
  void setup() {
    connection = new GrpcConnection(grpcClient);
  }

  @Test
  public void sendRequestAuthenticate() {
    String password = "pass123";
    String authToken = "asds-adsad-12345-6789";
    when(grpcClient.callAuthenticate(password)).thenReturn(authToken);

    String result = connection.sendRequestAuthenticate(password);
    assertEquals(authToken, result);
    verify(grpcClient).callAuthenticate(password);
  }
}
