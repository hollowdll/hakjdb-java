package com.github.hakjdb.hakjdbjava;

public final class ClientConfig {
    private final String defaultDatabase;
    private final int requestTimeoutSeconds;
    private final int disconnectWaitTimeSeconds;
    private final String password;
    // TODO: SSL/TLS configs

    private ClientConfig(String defaultDatabase, int requestTimeoutSeconds, String password, int disconnectWaitTimeSeconds) {
        this.defaultDatabase = defaultDatabase;
        this.requestTimeoutSeconds = requestTimeoutSeconds;
        this.disconnectWaitTimeSeconds = disconnectWaitTimeSeconds;
        this.password = password;
    }

    public String getDefaultDatabase() {
        return defaultDatabase;
    }

    public int getRequestTimeoutSeconds() {
        return requestTimeoutSeconds;
    }

    public int getDisconnectWaitTimeSeconds() {
        return disconnectWaitTimeSeconds;
    }

    public String getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String defaultDatabase = ConfigDefaults.DEFAULT_DATABASE;
        private int requestTimeoutSeconds = ConfigDefaults.DEFAULT_REQUEST_TIMEOUT_SECONDS;
        private int disconnectWaitTimeSeconds = ConfigDefaults.DEFAULT_DISCONNECT_WAIT_TIME_SECONDS;
        private String password = "";

        public ClientConfig build() {
            return new ClientConfig(defaultDatabase, requestTimeoutSeconds, password, disconnectWaitTimeSeconds);
        }

        public Builder defaultDatabase(String defaultDatabase) {
            this.defaultDatabase = defaultDatabase;
            return this;
        }

        public Builder requestTimeoutSeconds(int requestTimeoutSeconds) {
            this.requestTimeoutSeconds = requestTimeoutSeconds;
            return this;
        }

        public Builder disconnectWaitTimeSeconds(int disconnectWaitTimeSeconds) {
            this.disconnectWaitTimeSeconds = disconnectWaitTimeSeconds;
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
                "defaultDatabase='" + defaultDatabase + '\'' +
                ", requestTimeoutSeconds=" + requestTimeoutSeconds +
                ", password='" + password + '\'' +
                '}';
    }
}