package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrpcClientTest {
    private EchoGrpcClient mockedEchoClient;
    private GrpcClient grpcClient;
    private static ClientConfig config;

    @BeforeAll
    static void init() {
        config = ClientConfig.builder().build();
    }

    @BeforeEach
    void setup() {
        mockedEchoClient = Mockito.mock(EchoGrpcClient.class);
        grpcClient = new DefaultGrpcClient(config, mockedEchoClient);
    }

    @Test
    public void callUnaryEcho() {
        String message = "hello";
        Echo.UnaryEchoResponse response = Echo.UnaryEchoResponse.newBuilder().setMsg(message).build();
        when(mockedEchoClient.unaryEcho(message, grpcClient.getRequestTimeoutSeconds(), grpcClient.getRequestMetadata()))
                .thenReturn(response);

        String result = grpcClient.callUnaryEcho(message);
        assertEquals(message, result);
        verify(mockedEchoClient).unaryEcho(message, grpcClient.getRequestTimeoutSeconds(), grpcClient.getRequestMetadata());
    }
}
