package com.github.hakjdb.hakjdbjava.grpc;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import io.grpc.Metadata;

import java.util.concurrent.atomic.AtomicReference;

public class GrpcRequestMetadata {
    private final AtomicReference<Metadata> metadataRef;

    public GrpcRequestMetadata() {
        this.metadataRef = new AtomicReference<>(new Metadata());
    }

    public GrpcRequestMetadata(ClientConfig config) {
        this.metadataRef = new AtomicReference<>(new Metadata());
        setDatabase(config.getDefaultDatabase());
    }

    public GrpcRequestMetadata(Metadata metadata) {
        this.metadataRef = new AtomicReference<>(metadata);
    }

    public AtomicReference<Metadata> getMetadata() {
        return metadataRef;
    }

    public String getDatabase() {
        return metadataRef.get().get(GrpcMetadataKeys.DATABASE);
    }

    public String getAuthToken() {
        return metadataRef.get().get(GrpcMetadataKeys.AUTH_TOKEN);
    }

    public void setDatabase(String database) {
        metadataRef.get().put(GrpcMetadataKeys.DATABASE, database);
    }

    public void setAuthToken(String authToken) {
        metadataRef.get().put(GrpcMetadataKeys.AUTH_TOKEN, authToken);
    }

    @Override
    public String toString() {
        return "GrpcRequestMetadata{" +
                "database='" + getDatabase() + '\'' +
                ", authToken='" + getAuthToken() + '\'' +
                '}';
    }
}
