package com.github.hakjdb.hakjdbjava;

public final class ClientConfig {
  private final String defaultDatabase;
  private final int requestTimeoutSeconds;
  private final int disconnectWaitTimeSeconds;
  private final String password;
  private final boolean usePassword;
  private final boolean useTLS;
  private final boolean useClientCertAuth;
  private final String tlsCACertPath;
  private final String tlsClientCertPath;
  private final String tlsClientKeyPath;

  private ClientConfig(
      String defaultDatabase,
      int requestTimeoutSeconds,
      int disconnectWaitTimeSeconds,
      String password,
      boolean usePassword,
      boolean useTLS,
      boolean useClientCertAuth,
      String tlsCACertPath,
      String tlsClientCertPath,
      String tlsClientKeyPath) {
    this.defaultDatabase = defaultDatabase;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
    this.disconnectWaitTimeSeconds = disconnectWaitTimeSeconds;
    this.password = password;
    this.usePassword = usePassword;
    this.useTLS = useTLS;
    this.useClientCertAuth = useClientCertAuth;
    this.tlsCACertPath = tlsCACertPath;
    this.tlsClientCertPath = tlsClientCertPath;
    this.tlsClientKeyPath = tlsClientKeyPath;
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

  public boolean isUseTLS() {
    return useTLS;
  }

  public boolean isUseClientCertAuth() {
    return useClientCertAuth;
  }

  public String getTlsCACertPath() {
    return tlsCACertPath;
  }

  public String getTlsClientCertPath() {
    return tlsClientCertPath;
  }

  public String getTlsClientKeyPath() {
    return tlsClientKeyPath;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String defaultDatabase = ConfigDefaults.DEFAULT_DATABASE;
    private int requestTimeoutSeconds = ConfigDefaults.DEFAULT_REQUEST_TIMEOUT_SECONDS;
    private int disconnectWaitTimeSeconds = ConfigDefaults.DEFAULT_DISCONNECT_WAIT_TIME_SECONDS;
    private String password = ConfigDefaults.DEFAULT_PASSWORD;
    private boolean usePassword = ConfigDefaults.DEFAULT_USE_PASSWORD;
    private boolean useTLS = ConfigDefaults.DEFAULT_USE_TLS;
    private boolean useClientCertAuth = ConfigDefaults.DEFAULT_USE_CLIENT_CERT_AUTH;
    private String tlsCACertPath = ConfigDefaults.DEFAULT_TLS_CA_CERT_PATH;
    private String tlsClientCertPath = ConfigDefaults.DEFAULT_TLS_CLIENT_CERT_PATH;
    private String tlsClientKeyPath = ConfigDefaults.DEFAULT_TLS_CLIENT_KEY_PATH;

    public ClientConfig build() {
      return new ClientConfig(
          defaultDatabase,
          requestTimeoutSeconds,
          disconnectWaitTimeSeconds,
          password,
          usePassword,
          useTLS,
          useClientCertAuth,
          tlsCACertPath,
          tlsClientCertPath,
          tlsClientKeyPath);
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

    public Builder useTLS(boolean useTLS) {
      this.useTLS = useTLS;
      return this;
    }

    public Builder useClientCertAuth(boolean useClientCertAuth) {
      this.useClientCertAuth = useClientCertAuth;
      return this;
    }

    public Builder tlsCACertPath(String tlsCACertPath) {
      this.tlsCACertPath = tlsCACertPath;
      return this;
    }

    public Builder tlsClientCertPath(String tlsClientCertPath) {
      this.tlsClientCertPath = tlsClientCertPath;
      return this;
    }

    public Builder tlsClientKeyPath(String tlsClientKeyPath) {
      this.tlsClientKeyPath = tlsClientKeyPath;
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
        + ", useTLS="
        + useTLS
        + ", useClientCertAuth="
        + useClientCertAuth
        + ", tlsCACertPath='"
        + tlsCACertPath
        + '\''
        + ", tlsClientCertPath='"
        + tlsClientCertPath
        + '\''
        + ", tlsClientKeyPath='"
        + tlsClientKeyPath
        + '\''
        + '}';
  }
}
