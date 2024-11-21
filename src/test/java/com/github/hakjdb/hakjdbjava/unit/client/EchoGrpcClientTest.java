package com.github.hakjdb.hakjdbjava.unit.client;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;

import com.github.hakjdb.hakjdbjava.grpc.EchoGrpcClient;
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
public class EchoGrpcClientTest {
  @Mock private EchoGrpcClient echoClient;
  private GrpcClient grpcClient;

  @BeforeEach
  void setup() {
    grpcClient = new GrpcClient(null, null, 0, "", echoClient, null, null);
  }

  @Test
  public void callUnaryEcho() {
    String message = "hello";
    Echo.UnaryEchoRequest request = Echo.UnaryEchoRequest.newBuilder().setMsg(message).build();
    Echo.UnaryEchoResponse response = Echo.UnaryEchoResponse.newBuilder().setMsg(message).build();

    when(echoClient.unaryEcho(request, grpcClient.getRequestTimeoutSeconds())).thenReturn(response);

    String result = grpcClient.callUnaryEcho(message);
    assertEquals(message, result);
    verify(echoClient).unaryEcho(request, grpcClient.getRequestTimeoutSeconds());
  }
}
