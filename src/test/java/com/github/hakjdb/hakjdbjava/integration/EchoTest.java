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

  @BeforeAll
  static void setUp() {
    sharedHakjdbContainer.start();
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
  }
}
