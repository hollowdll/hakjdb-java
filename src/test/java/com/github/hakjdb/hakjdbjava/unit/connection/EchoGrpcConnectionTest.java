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
public class EchoGrpcConnectionTest {
    @Mock private GrpcClient grpcClient;
    private GrpcConnection connection;

    @BeforeEach
    void setup() {
        connection = new GrpcConnection(grpcClient);
    }

    @Test
    public void sendRequestEcho() {
        String message = "hello";
        when(grpcClient.callUnaryEcho(message)).thenReturn(message);

        String result = connection.sendRequestEcho(message);
        assertEquals(message, result);
        verify(grpcClient).callUnaryEcho(message);
    }
}
