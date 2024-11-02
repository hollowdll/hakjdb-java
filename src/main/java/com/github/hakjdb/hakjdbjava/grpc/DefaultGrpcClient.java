package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

public class DefaultGrpcClient implements GrpcClient {
    private final ManagedChannel channel;
    private final EchoGrpcClient echoClient;
    private final GrpcRequestMetadata requestMetadata;
    private final int requestTimeoutSeconds;

    public DefaultGrpcClient(String host, int port, ClientConfig config) {
        this.channel = ManagedChannelFactory.createInsecureChannel(host, port);
        this.echoClient = new DefaultEchoGrpcClient(this.channel);
        this.requestMetadata = GrpcRequestMetadata.builder().build();
        this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
    }

    public DefaultGrpcClient(String host, int port, ClientConfig config, EchoGrpcClient echoClient) {
        this.channel = ManagedChannelFactory.createInsecureChannel(host, port);
        this.echoClient = echoClient;
        this.requestMetadata = GrpcRequestMetadata.builder().build();
        this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
    }

    @Override
    public void shutdown(long waitTimeSeconds) {
        if (!channel.isShutdown()) {
            try {
                channel.shutdown().awaitTermination(waitTimeSeconds, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                channel.shutdownNow();
            } finally {
                if (!channel.isShutdown()) {
                    channel.shutdownNow();
                }
            }
        }
    }

    @Override
    public String callUnaryEcho(String message) {
        Echo.UnaryEchoResponse response = echoClient.unaryEcho(message, requestTimeoutSeconds, requestMetadata);
        return response.getMsg();
    }
}
