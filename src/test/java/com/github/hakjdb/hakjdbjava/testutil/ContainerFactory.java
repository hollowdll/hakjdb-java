package com.github.hakjdb.hakjdbjava.testutil;

import org.testcontainers.containers.GenericContainer;

public class ContainerFactory {
  public static GenericContainer<?> createDefaultHakjDBContainer() {
    return new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
        .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT);
  }

  public static GenericContainer<?> createAuthTokenExpireHakjDBContainer() {
    return new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
        .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT)
        .withEnv("HAKJ_AUTH_ENABLED", "true")
        .withEnv("HAKJ_AUTH_TOKEN_TTL", "1");
  }
}
