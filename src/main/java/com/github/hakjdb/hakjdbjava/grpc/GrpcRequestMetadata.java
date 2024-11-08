package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.Metadata;

public class GrpcRequestMetadata {
    private final Metadata metadata;

    public GrpcRequestMetadata() {
        this.metadata = new Metadata();
    }

    public GrpcRequestMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public String getDatabase() {
        return metadata.get(GrpcMetadataKeys.DATABASE);
    }

    public String getAuthToken() {
        return metadata.get(GrpcMetadataKeys.AUTH_TOKEN);
    }

    public void setDatabase(String database) {
        metadata.put(GrpcMetadataKeys.DATABASE, database);
    }

    public void setAuthToken(String authToken) {
        metadata.put(GrpcMetadataKeys.AUTH_TOKEN, authToken);
    }

    @Override
    public String toString() {
        return "GrpcRequestMetadata{" +
                "database='" + getDatabase() + '\'' +
                ", authToken='" + getAuthToken() + '\'' +
                '}';
    }
}
