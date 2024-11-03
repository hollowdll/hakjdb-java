package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    @Mock
    private GrpcClient grpcClient;

    @Test
    public void sendRequestEcho() {
        Connection connection = new Connection(grpcClient);
        String message = "hello";
        when(grpcClient.callUnaryEcho(message)).thenReturn(message);

        String result = connection.sendRequestEcho(message);
        assertEquals(message, result);
    }
}
