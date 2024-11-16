package com.github.hakjdb.hakjdbjava.grpc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrpcConnectionTest {
    private GrpcClient mockedGrpcClient;
    private GrpcConnection connection;

    @BeforeEach
    void setup() {
        mockedGrpcClient = Mockito.mock(GrpcClient.class);
        connection = new GrpcConnection(mockedGrpcClient);
    }

    @Test
    public void sendRequestAuthenticate() {
        String password = "pass123";
        String authToken = "asds-adsad-12345-6789";
        when(mockedGrpcClient.callAuthenticate(password)).thenReturn(authToken);

        String result = connection.sendRequestAuthenticate(password);
        assertEquals(authToken, result);
        verify(mockedGrpcClient).callAuthenticate(password);
    }

    @Test
    public void sendRequestEcho() {
        String message = "hello";
        when(mockedGrpcClient.callUnaryEcho(message)).thenReturn(message);

        String result = connection.sendRequestEcho(message);
        assertEquals(message, result);
        verify(mockedGrpcClient).callUnaryEcho(message);
    }

    @Test
    public void sendRequestSet() {
        String key = "key1";
        String value = "Hello!";
        connection.sendRequestSet(key, value);
        verify(mockedGrpcClient).callSetString(key, value);
    }

    @Test
    public void sendRequestGet() {
        String key = "key1";
        String value = "Hello!";
        when(mockedGrpcClient.callGetString(key)).thenReturn(value);

        String result = connection.sendRequestGet(key);
        assertEquals(value, result);
        verify(mockedGrpcClient).callGetString(key);
    }

    @Test
    public void sendRequestGetKeyNotFound() {
        String key = "key1";
        when(mockedGrpcClient.callGetString(key)).thenReturn(null);

        String result = connection.sendRequestGet(key);
        assertNull(result);
        verify(mockedGrpcClient).callGetString(key);
    }
}
