package com.github.hakjdb.hakjdbjava;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class HakjDBTest {
  private final int containerPort = 12345;
  private final String image = "hakj/hakjdb:v1.2.0-alpine";

  @Test
  public void connects() {
    assertDoesNotThrow(
        () -> {
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(image).withExposedPorts(containerPort);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String host = hakjdbContainer.getHost();
          HakjDB hakjdb = new HakjDB(host, mappedPort);
          assertNotNull(hakjdb);
        });
  }

  @Test
  public void disconnects() {
    assertDoesNotThrow(
        () -> {
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(image).withExposedPorts(containerPort);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String host = hakjdbContainer.getHost();
          HakjDB hakjdb = new HakjDB(host, mappedPort);
          assertNotNull(hakjdb);
          hakjdb.disconnect();
          assertThrows(
              RuntimeException.class,
              () -> {
                hakjdb.echo("");
              });
        });
  }

  @Test
  public void echo() {
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image).withExposedPorts(containerPort);
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();
    // System.out.printf("[Testcontainers] HakjDB container running at: %s:%d\n", containerHost,
    // mappedPort);

    HakjDB hakjdb = new HakjDB(host, mappedPort);
    String message = "Hello World!";
    String result = hakjdb.echo(message);
    assertEquals(message, result);
  }

  @Test
  public void setGetString() {
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image).withExposedPorts(containerPort);
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();

    HakjDB hakjdb = new HakjDB(host, mappedPort);
    String key = "key123";
    String value = "Hello";
    hakjdb.set(key, value);
    String result = hakjdb.get(key);
    assertEquals(value, result);
  }

  @Test
  public void setGetStringNonDefaultDatabase() {
    String database = "test_db";
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image)
            .withExposedPorts(containerPort)
            .withEnv("HAKJ_DEFAULT_DB", database);
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();

    ClientConfig config = ClientConfig.builder().defaultDatabase(database).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);
    String key = "key123";
    String value = "Hello?";
    hakjdb.set(key, value);
    String result = hakjdb.get(key);
    assertEquals(value, result);
  }
}
