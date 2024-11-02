package com.github.hakjdb.hakjdbjava;

public class HakjDB implements EchoRequests {
    private final Connection connection;

    public HakjDB(String host, int port) {
        this.connection = new Connection(host, port);
    }

    public HakjDB(String host, int port, ClientConfig config) {
        this.connection = new Connection(host, port, config);
    }

    @Override
    public String echo(String message) {
        return connection.sendRequestEcho(message);
    }
}