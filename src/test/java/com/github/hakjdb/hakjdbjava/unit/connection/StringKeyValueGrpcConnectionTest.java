package com.github.hakjdb.hakjdbjava.unit.connection;

import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;
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
public class StringKeyValueGrpcConnectionTest {
    @Mock private GrpcClient grpcClient;
    private GrpcConnection connection;

    @BeforeEach
    void setup() {
        connection = new GrpcConnection(grpcClient);
    }

    @Test
    public void sendRequestSet() {
        String key = "key1";
        String value = "Hello!";
        connection.sendRequestSet(key, value);
        verify(grpcClient).callSetString(key, value);
    }

    @Test
    public void sendRequestGet() {
        String key = "key1";
        String value = "Hello!";
        when(grpcClient.callGetString(key)).thenReturn(value);

        String result = connection.sendRequestGet(key);
        assertEquals(value, result);
        verify(grpcClient).callGetString(key);
    }

    @Test
    public void sendRequestGetKeyNotFound() {
        String key = "key1";
        when(grpcClient.callGetString(key)).thenReturn(null);

        String result = connection.sendRequestGet(key);
        assertNull(result);
        verify(grpcClient).callGetString(key);
    }
}
