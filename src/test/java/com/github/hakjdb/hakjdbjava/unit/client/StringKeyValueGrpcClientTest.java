package com.github.hakjdb.hakjdbjava.unit.client;

import com.github.hakjdb.hakjdbjava.api.v1.kvpb.StringKv;
import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.StringKeyValueGrpcClient;
import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StringKeyValueGrpcClientTest {
  @Mock private StringKeyValueGrpcClient stringKeyValueGrpcClient;
  private GrpcClient grpcClient;

  @BeforeEach
  void setup() {
    grpcClient = new GrpcClient(null, null, 0, "", null, stringKeyValueGrpcClient, null, null);
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

    when(stringKeyValueGrpcClient.setString(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    grpcClient.callSetString(key, value);
    verify(stringKeyValueGrpcClient).setString(request, grpcClient.getRequestTimeoutSeconds());
  }

  @Test
  public void callGetString() {
    String key = "key1";
    String value = "Hello world!";
    StringKv.GetStringRequest request = StringKv.GetStringRequest.newBuilder().setKey(key).build();
    StringKv.GetStringResponse response =
        StringKv.GetStringResponse.newBuilder()
            .setValue(ByteString.copyFromUtf8(value))
            .setOk(true)
            .build();

    when(stringKeyValueGrpcClient.getString(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    String result = grpcClient.callGetString(key);
    assertEquals(value, result);
    verify(stringKeyValueGrpcClient).getString(request, grpcClient.getRequestTimeoutSeconds());
  }

  @Test
  public void callGetStringKeyNotFound() {
    String key = "key1";
    StringKv.GetStringRequest request = StringKv.GetStringRequest.newBuilder().setKey(key).build();
    StringKv.GetStringResponse response =
        StringKv.GetStringResponse.newBuilder().setValue(ByteString.empty()).setOk(false).build();

    when(stringKeyValueGrpcClient.getString(request, grpcClient.getRequestTimeoutSeconds()))
        .thenReturn(response);

    String result = grpcClient.callGetString(key);
    assertNull(result);
    verify(stringKeyValueGrpcClient).getString(request, grpcClient.getRequestTimeoutSeconds());
  }
}
