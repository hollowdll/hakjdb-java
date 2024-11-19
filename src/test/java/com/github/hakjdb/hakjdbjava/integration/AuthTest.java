package com.github.hakjdb.hakjdbjava.integration;

import com.github.hakjdb.hakjdbjava.HakjDB;
import com.github.hakjdb.hakjdbjava.testutil.TestDefaults;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class AuthTest {
  @Test
  public void authenticate() {
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
            .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT)
            .withEnv("HAKJ_AUTH_ENABLED", "true");
    hakjdbContainer.start();

    HakjDB hakjdb =
        new HakjDB(
            hakjdbContainer.getHost(),
            hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT));
    String password = "";
    String result = hakjdb.authenticate(password);
    assertNotNull(result);
    assertNotEquals("", result);
  }
}
