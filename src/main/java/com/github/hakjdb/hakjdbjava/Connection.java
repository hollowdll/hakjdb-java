package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.api.v1.echopb.Echo;
import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;
import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;

public class Connection {
    private final GrpcClient grpcClient;
    private final ClientConfig config;

    public Connection(String host, int port) {
        this.grpcClient = new GrpcClient(host, port);
        this.config = ClientConfig.builder().build();
    }

    public Connection(String host, int port, ClientConfig config) {
        this.grpcClient = new GrpcClient(host, port);
        this.config = config;
    }

    public void connect() throws ConnectionException {
        // TODO
    }

    public void disconnect() throws ConnectionException {
        // TODO
    }

    public GrpcClient getGrpcClient() {
        return grpcClient;
    }

    public String sendRequestEcho(String message) {
        Echo.UnaryEchoResponse response = grpcClient.unaryEcho(message, config.getRequestTimeoutSeconds());
        return response.getMsg();
    }
}
