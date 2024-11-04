package com.github.hakjdb.hakjdbjava;

public class HakjDB implements EchoRequests {
    private final Connection connection;

    public HakjDB() {
        this.connection = new Connection();
        this.connection.connect();
    }

    public HakjDB(String host, int port) {
        this.connection = new Connection(host, port);
        this.connection.connect();
    }

    public HakjDB(String host, int port, ClientConfig config) {
        this.connection = new Connection(host, port, config);
        this.connection.connect();
    }

    public void disconnect() {
        this.connection.disconnect();
    }

    @Override
    public String echo(String message) {
        return connection.sendRequestEcho(message);
    }
}