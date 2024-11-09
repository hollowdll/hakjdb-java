package com.github.hakjdb.hakjdbjava;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class HakjDBTest {
  private final int containerPort = 12345;

  @Container
  public GenericContainer<?> hakjdbContainer =
      new GenericContainer<>("hakj/hakjdb:v1.2.0-alpine").withExposedPorts(containerPort);

  @Test
  public void connects() {
    assertDoesNotThrow(
        () -> {
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String containerHost = hakjdbContainer.getHost();
          HakjDB hakjdb = new HakjDB(containerHost, mappedPort);
          assertNotNull(hakjdb);
        });
  }

  @Test
  public void disconnects() {
    assertDoesNotThrow(
        () -> {
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String containerHost = hakjdbContainer.getHost();
          HakjDB hakjdb = new HakjDB(containerHost, mappedPort);
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
  public void echoRequest() {
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String containerHost = hakjdbContainer.getHost();
    // System.out.printf("[Testcontainers] HakjDB container running at: %s:%d\n", containerHost,
    // mappedPort);

    HakjDB hakjdb = new HakjDB(containerHost, mappedPort);
    String message = "Hello World!";
    String result = hakjdb.echo(message);
    assertEquals(message, result);
  }
}
