package com.github.hakjdb.hakjdbjava.testutil;

import org.testcontainers.containers.GenericContainer;

public class ContainerFactory {
  public static GenericContainer<?> createDefaultHakjDBContainer() {
    return new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
        .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT);
  }
}
