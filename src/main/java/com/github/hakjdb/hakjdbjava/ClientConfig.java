package com.github.hakjdb.hakjdbjava;

public final class ClientConfig {
  private final String defaultDatabase;
  private final int requestTimeoutSeconds;
  private final int disconnectWaitTimeSeconds;
  private final String password;
  private final boolean usePassword;

  // TODO: SSL/TLS configs

  private ClientConfig(
      String defaultDatabase,
      int requestTimeoutSeconds,
      int disconnectWaitTimeSeconds,
      String password,
      boolean usePassword) {
    this.defaultDatabase = defaultDatabase;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
    this.disconnectWaitTimeSeconds = disconnectWaitTimeSeconds;
    this.password = password;
    this.usePassword = usePassword;
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

  public boolean isUsePassword() {
    return usePassword;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String defaultDatabase = ConfigDefaults.DEFAULT_DATABASE;
    private int requestTimeoutSeconds = ConfigDefaults.DEFAULT_REQUEST_TIMEOUT_SECONDS;
    private int disconnectWaitTimeSeconds = ConfigDefaults.DEFAULT_DISCONNECT_WAIT_TIME_SECONDS;
    private String password = "";
    private boolean usePassword = ConfigDefaults.DEFAULT_USE_PASSWORD;

    public ClientConfig build() {
      return new ClientConfig(
          defaultDatabase, requestTimeoutSeconds, disconnectWaitTimeSeconds, password, usePassword);
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

    public Builder usePassword(boolean usePassword) {
      this.usePassword = usePassword;
      return this;
    }
  }

  @Override
  public String toString() {
    return "ClientConfig{"
        + "defaultDatabase='"
        + defaultDatabase
        + '\''
        + ", requestTimeoutSeconds="
        + requestTimeoutSeconds
        + ", disconnectWaitTimeSeconds="
        + disconnectWaitTimeSeconds
        + ", password='"
        + password
        + '\''
        + ", usePassword="
        + usePassword
        + '}';
  }
}
