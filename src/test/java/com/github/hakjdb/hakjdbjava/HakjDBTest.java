package com.github.hakjdb.hakjdbjava;

import com.github.hakjdb.hakjdbjava.exceptions.HakjDBConnectionException;
import com.github.hakjdb.hakjdbjava.exceptions.HakjDBRequestException;
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
          assertThrows(HakjDBRequestException.class, () -> hakjdb.echo(""));
        });
  }

  @Test
  public void connectAuthEnabled() {
    assertDoesNotThrow(
        () -> {
          String password = "pass1234";
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(image)
                  .withExposedPorts(containerPort)
                  .withEnv("HAKJ_AUTH_ENABLED", "true")
                  .withEnv("HAKJ_PASSWORD", password);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String host = hakjdbContainer.getHost();

          ClientConfig config = ClientConfig.builder().usePassword(true).password(password).build();
          new HakjDB(host, mappedPort, config);
        });
  }

  @Test
  public void connectAuthEnabledNotUsingPasswordShouldThrow() {
    assertThrows(
        HakjDBConnectionException.class,
        () -> {
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(image)
                  .withExposedPorts(containerPort)
                  .withEnv("HAKJ_AUTH_ENABLED", "true")
                  .withEnv("HAKJ_PASSWORD", "pass1234");
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String host = hakjdbContainer.getHost();

          ClientConfig config = ClientConfig.builder().build();
          new HakjDB(host, mappedPort, config);
        });
  }

  @Test
  public void connectAuthNotEnabledUsingPasswordShouldThrow() {
    assertThrows(
        HakjDBConnectionException.class,
        () -> {
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(image).withExposedPorts(containerPort);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
          String host = hakjdbContainer.getHost();

          ClientConfig config = ClientConfig.builder().usePassword(true).build();
          new HakjDB(host, mappedPort, config);
        });
  }

  @Test
  public void authenticate() {
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image)
            .withExposedPorts(containerPort)
            .withEnv("HAKJ_AUTH_ENABLED", "true");
    ;
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();

    HakjDB hakjdb = new HakjDB(host, mappedPort);
    String password = "";
    String result = hakjdb.authenticate(password);
    assertNotNull(result);
    assertNotEquals("", result);
  }

  @Test
  public void echo() {
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image).withExposedPorts(containerPort);
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();

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

  @Test
  public void setStringDatabaseNotFoundShouldThrow() {
    String database = "test_db";
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image).withExposedPorts(containerPort);
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();

    ClientConfig config = ClientConfig.builder().defaultDatabase(database).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);
    assertThrows(HakjDBRequestException.class, () -> hakjdb.set("key1", "value"));
  }

  @Test
  public void setStringObtainNewAuthTokenAfterExpire() {
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(image)
            .withExposedPorts(containerPort)
            .withEnv("HAKJ_AUTH_ENABLED", "true")
            .withEnv("HAKJ_AUTH_TOKEN_TTL", "1");
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(containerPort);
    String host = hakjdbContainer.getHost();

    ClientConfig config = ClientConfig.builder().usePassword(true).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);

    assertDoesNotThrow(
        () -> {
          Thread.sleep(1100);
          hakjdb.set("key1", "value");
        });
  }
}
