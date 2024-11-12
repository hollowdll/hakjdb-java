package com.github.hakjdb.hakjdbjava.grpc;

import io.grpc.Metadata;

public final class GrpcMetadataKeys {
  public static final String NAME_AUTH_TOKEN = "auth-token";
  public static final String NAME_DATABASE = "database";
  public static final String NAME_API_VERSION = "api-version";

  public static final Metadata.Key<String> AUTH_TOKEN =
      Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
  public static final Metadata.Key<String> DATABASE =
      Metadata.Key.of("database", Metadata.ASCII_STRING_MARSHALLER);
  public static final Metadata.Key<String> API_VERSION =
      Metadata.Key.of("api-version", Metadata.ASCII_STRING_MARSHALLER);
}
