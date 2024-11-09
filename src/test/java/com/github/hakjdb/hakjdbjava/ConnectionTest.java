package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;

import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionTest {
  private GrpcClient mockedGrpcClient;
  private Connection connection;

  @BeforeEach
  void setup() {
    mockedGrpcClient = Mockito.mock(GrpcClient.class);
    connection = new GrpcConnection(mockedGrpcClient);
  }

  @Test
  public void sendRequestEcho() {
    String message = "hello";
    when(mockedGrpcClient.callUnaryEcho(message)).thenReturn(message);

    String result = connection.sendRequestEcho(message);
    assertEquals(message, result);
    verify(mockedGrpcClient).callUnaryEcho(message);
  }
}
