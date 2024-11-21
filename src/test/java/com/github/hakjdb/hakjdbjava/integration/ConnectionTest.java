package com.github.hakjdb.hakjdbjava.integration;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.HakjDB;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBRequestException;
import com.github.hakjdb.hakjdbjava.testutil.ContainerFactory;
import com.github.hakjdb.hakjdbjava.testutil.TestDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class ConnectionTest {
  @Container
  private static final GenericContainer<?> sharedHakjdbContainer =
      ContainerFactory.createDefaultHakjDBContainer();

  @BeforeAll
  static void setUp() {
    sharedHakjdbContainer.start();
  }

  @Test
  public void connects() {
    assertDoesNotThrow(
        () -> {
          HakjDB hakjdb =
              new HakjDB(
                  sharedHakjdbContainer.getHost(),
                  sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT));
          assertNotNull(hakjdb);
          hakjdb.disconnect();
        });
  }

  @Test
  public void disconnects() {
    assertDoesNotThrow(
        () -> {
          HakjDB hakjdb =
              new HakjDB(
                  sharedHakjdbContainer.getHost(),
                  sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT));
          assertNotNull(hakjdb);
          hakjdb.disconnect();
          assertThrows(HakjDBRequestException.class, () -> hakjdb.echo(""));
        });
  }

  @Test
  public void connectAuthEnabled() {
    assertDoesNotThrow(
        () -> {
          String password = "pass1234";
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
                  .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT)
                  .withEnv("HAKJ_AUTH_ENABLED", "true")
                  .withEnv("HAKJ_PASSWORD", password);
          hakjdbContainer.start();

          ClientConfig config = ClientConfig.builder().usePassword(true).password(password).build();
          HakjDB hakjdb =
              new HakjDB(
                  hakjdbContainer.getHost(),
                  hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT),
                  config);
          hakjdb.disconnect();
        });
  }

  @Test
  public void connectAuthEnabledNotUsingPasswordShouldThrow() {
    assertThrows(
        HakjDBConnectionException.class,
        () -> {
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
                  .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT)
                  .withEnv("HAKJ_AUTH_ENABLED", "true")
                  .withEnv("HAKJ_PASSWORD", "pass1234");
          hakjdbContainer.start();

          ClientConfig config = ClientConfig.builder().build();
          HakjDB hakjdb =
              new HakjDB(
                  hakjdbContainer.getHost(),
                  hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT),
                  config);
          hakjdb.disconnect();
        });
  }

  @Test
  public void connectAuthNotEnabledUsingPasswordShouldThrow() {
    assertThrows(
        HakjDBConnectionException.class,
        () -> {
          ClientConfig config = ClientConfig.builder().usePassword(true).build();
          HakjDB hakjdb =
              new HakjDB(
                  sharedHakjdbContainer.getHost(),
                  sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT),
                  config);
          hakjdb.disconnect();
        });
  }
}
