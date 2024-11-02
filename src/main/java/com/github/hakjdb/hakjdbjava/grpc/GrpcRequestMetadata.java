package com.github.hakjdb.hakjdbjava.grpc;

public class GrpcRequestMetadata {
    private String database;
    private String authToken;

    public GrpcRequestMetadata() {
        this.database = "";
        this.authToken = "";
    }

    public GrpcRequestMetadata(String database) {
        this.database = database;
        this.authToken = "";
    }

    public GrpcRequestMetadata(String database, String authToken) {
        this.database = database;
        this.authToken = authToken;
    }

    public String getDatabase() {
        return database;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
