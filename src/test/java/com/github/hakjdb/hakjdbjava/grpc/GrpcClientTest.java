package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv;
import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrpcClientTest {
  private EchoGrpcClient mockedEchoClient;
  private StringKeyValueGrpcClient mockedStringKeyValueClient;
  private GrpcClient grpcClient;

  @BeforeEach
  void setup() {
    mockedEchoClient = Mockito.mock(EchoGrpcClient.class);
    mockedStringKeyValueClient = Mockito.mock(StringKeyValueGrpcClient.class);
    grpcClient = new GrpcClient(null, null, 0, mockedEchoClient, mockedStringKeyValueClient);
  }

  @Test
  public void callUnaryEcho() {
    String message = "hello";
    Echo.UnaryEchoRequest request = Echo.UnaryEchoRequest.newBuilder().setMsg(message).build();
    Echo.UnaryEchoResponse response = Echo.UnaryEchoResponse.newBuilder().setMsg(message).build();
    when(mockedEchoClient.unaryEcho(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    String result = grpcClient.callUnaryEcho(message);
    assertEquals(message, result);
    verify(mockedEchoClient).unaryEcho(request, grpcClient.getRequestTimeoutSeconds());
  }

  @Test
  public void callSetString() {
    String key = "key1";
    String value = "Hello world!";
    StringKv.SetStringRequest request =
        StringKv.SetStringRequest.newBuilder()
            .setKey(key)
            .setValue(ByteString.copyFromUtf8(value))
            .build();
    StringKv.SetStringResponse response = StringKv.SetStringResponse.newBuilder().build();
    when(mockedStringKeyValueClient.setString(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    grpcClient.callSetString(key, value);
    verify(mockedStringKeyValueClient).setString(request, grpcClient.getRequestTimeoutSeconds());
  }

  @Test
  public void callGetString() {
    String key = "key1";
    String value = "Hello world!";
    StringKv.GetStringRequest request = StringKv.GetStringRequest.newBuilder().setKey(key).build();
    StringKv.GetStringResponse response =
        StringKv.GetStringResponse.newBuilder().setValue(ByteString.copyFromUtf8(value)).build();
    when(mockedStringKeyValueClient.getString(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    String result = grpcClient.callGetString(key);
    assertEquals(value, result);
    verify(mockedStringKeyValueClient).getString(request, grpcClient.getRequestTimeoutSeconds());
  }
}
