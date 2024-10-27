package com.github.hakjdb.hakjdbjava;

public final class ClientConfig {
    private final String defaultDB;
    private final int requestTimeoutSeconds;
    private final String password;
    // TODO: SSL configs

    private ClientConfig(String defaultDB, int requestTimeoutSeconds, String password) {
        this.defaultDB = defaultDB;
        this.requestTimeoutSeconds = requestTimeoutSeconds;
        this.password = password;
    }

    public String getDefaultDB() {
        return defaultDB;
    }

    public int getRequestTimeoutSeconds() {
        return requestTimeoutSeconds;
    }

    public String getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String defaultDB = "default";
        private int requestTimeoutSeconds = 10;
        private String password;

        public ClientConfig build() {
            return new ClientConfig(defaultDB, requestTimeoutSeconds, password);
        }

        public Builder defaultDB(String defaultDB) {
            this.defaultDB = defaultDB;
            return this;
        }

        public Builder requestTimeoutSeconds(int requestTimeoutSeconds) {
            this.requestTimeoutSeconds = requestTimeoutSeconds;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }
    }

    @Override
    public String toString() {
        return "ClientConfig{" +
                "defaultDB='" + defaultDB + '\'' +
                ", requestTimeoutSeconds=" + requestTimeoutSeconds +
                ", password='" + password + '\'' +
                '}';
    }
}