package com.github.hakjdb.hakjdbjava.grpc;

public class GrpcRequestMetadata {
    private String database;
    private String authToken;

    private GrpcRequestMetadata(String database, String authToken) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String database = "default";
        private String authToken;

        public GrpcRequestMetadata build() {
            return new GrpcRequestMetadata(database, authToken);
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public Builder authToken(String authToken) {
            this.authToken = authToken;
            return this;
        }
    }

    @Override
    public String toString() {
        return "GrpcRequestMetadata{" +
                "database='" + database + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
