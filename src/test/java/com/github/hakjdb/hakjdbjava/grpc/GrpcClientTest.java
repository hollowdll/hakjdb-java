package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;

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
        grpcClient = new DefaultGrpcClient(null, null, 0, mockedEchoClient, mockedStringKeyValueClient);
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
}
