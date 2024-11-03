package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GrpcClientTest {
    @Mock
    private EchoGrpcClient echoClient;

    private final ClientConfig config = ClientConfig.builder().build();

    @Test
    public void callUnaryEcho() {
        GrpcClient grpcClient = new DefaultGrpcClient(config, echoClient);
        String message = "hello";
        Echo.UnaryEchoResponse response = Echo.UnaryEchoResponse.newBuilder().setMsg(message).build();
        when(echoClient.unaryEcho(message, grpcClient.getRequestTimeoutSeconds(), grpcClient.getRequestMetadata()))
                .thenReturn(response);

        String result = grpcClient.callUnaryEcho(message);
        assertEquals(message, result);
    }
}
