package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;

import java.util.concurrent.TimeUnit;

public class DefaultGrpcClient implements GrpcClient {
    private final ManagedChannel channel;
    private final EchoGrpcClient echoClient;
    private final GrpcRequestMetadata requestMetadata;
    private final int requestTimeoutSeconds;

    public DefaultGrpcClient(String host, int port, ClientConfig config) {
        this.channel = ManagedChannelFactory.createInsecureChannel(host, port);
        this.requestMetadata = new GrpcRequestMetadata(config);
        HeaderClientInterceptor interceptor = new HeaderClientInterceptor(requestMetadata.getMetadata());
        Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);
        this.echoClient = new DefaultEchoGrpcClient(interceptedChannel);
        this.requestTimeoutSeconds = config.getRequestTimeoutSeconds();
    }

    public DefaultGrpcClient(ManagedChannel channel, GrpcRequestMetadata requestMetadata, int requestTimeoutSeconds, EchoGrpcClient echoClient) {
        this.channel = channel;
        this.echoClient = echoClient;
        this.requestMetadata = requestMetadata;
        this.requestTimeoutSeconds = requestTimeoutSeconds;
    }

    public int getRequestTimeoutSeconds() {
        return requestTimeoutSeconds;
    }

    public GrpcRequestMetadata getRequestMetadata() {
        return requestMetadata;
    }

    @Override
    public void shutdown(long waitTimeSeconds) {
        if (channel != null && !channel.isShutdown()) {
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
