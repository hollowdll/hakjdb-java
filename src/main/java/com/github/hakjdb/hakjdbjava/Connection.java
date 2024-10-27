package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.exceptions.ConnectionException;
import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;

public class Connection {
    private final GrpcClient client;
    private final ClientConfig config;

    public Connection(String host, int port) {
        this.client = new GrpcClient(host, port);
        this.config = ClientConfig.builder().build();
    }

    public Connection(String host, int port, ClientConfig config) {
        this.client = new GrpcClient(host, port);
        this.config = config;
    }

    public void connect() throws ConnectionException {
        // TODO
    }

    public void disconnect() throws ConnectionException {
        // TODO
    }
}
