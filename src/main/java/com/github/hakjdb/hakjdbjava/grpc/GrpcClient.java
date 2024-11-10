package com.github.hakjdb.hakjdbjava.grpc;

public interface GrpcClient {
  int getRequestTimeoutSeconds();
  GrpcRequestMetadata getRequestMetadata();

  /**
   * Shuts down the client channel.
   *
   * @param waitTimeSeconds Time to wait in seconds
   */
  void shutdown(long waitTimeSeconds);

  /**
   * Calls the UnaryEcho RPC handler.
   *
   * @param message Message to send
   * @return Received message
   */
  String callUnaryEcho(String message);

  /**
   * Calls the SetString RPC handler.
   *
   * @param key Key to use
   * @param value Value to store
   */
  void callSetString(String key, String value);

  /**
   * Calls the GetString RPC handler.
   *
   * @param key Key to use
   * @return Retrieved value
   */
  String callGetString(String key);
}
