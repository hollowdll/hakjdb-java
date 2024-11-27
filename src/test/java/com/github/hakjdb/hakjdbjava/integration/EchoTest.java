package com.github.hakjdb.hakjdbjava.integration;

import com.github.hakjdb.hakjdbjava.HakjDB;
import com.github.hakjdb.hakjdbjava.testutil.ContainerFactory;
import com.github.hakjdb.hakjdbjava.testutil.TestDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class EchoTest {
  @Container
  private static final GenericContainer<?> sharedHakjdbContainer =
      ContainerFactory.createDefaultHakjDBContainer();

  @Container
  private static final GenericContainer<?> authTokenExpireHakjdbContainer =
      ContainerFactory.createAuthTokenExpireHakjDBContainer();

  @BeforeAll
  static void setUp() {
    sharedHakjdbContainer.start();
    authTokenExpireHakjdbContainer.start();
  }

  @Test
  public void echo() {
    HakjDB hakjdb =
        new HakjDB(
            sharedHakjdbContainer.getHost(),
            sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT));
    String message = "Hello World!";
    String result = hakjdb.echo(message);
    assertEquals(message, result);
    hakjdb.disconnectNow();
  }

  @Test
  public void echoObtainNewAuthTokenAfterExpire() {
    Integer mappedPort =
        authTokenExpireHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
    String host = authTokenExpireHakjdbContainer.getHost();
    HakjDB hakjdb = new HakjDB(host, mappedPort);

    assertDoesNotThrow(
        () -> {
          Thread.sleep(1100);
          hakjdb.echo("message");
          hakjdb.disconnectNow();
        });
  }
}
