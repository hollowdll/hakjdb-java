package com.github.hakjdb.hakjdbjava.integration;

import com.github.hakjdb.hakjdbjava.ClientConfig;
import com.github.hakjdb.hakjdbjava.HakjDB;
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
public class StringKeyValueTest {
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
  public void setGetString() {
    Integer mappedPort = sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
    String host = sharedHakjdbContainer.getHost();
    HakjDB hakjdb = new HakjDB(host, mappedPort);
    String key = "key1";
    String value = "Hello";
    hakjdb.set(key, value);
    String result = hakjdb.get(key);
    assertEquals(value, result);
    hakjdb.disconnectNow();
  }

  @Test
  public void setGetStringNonDefaultDatabase() {
    String database = "test_db";
    GenericContainer<?> hakjdbContainer =
        new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
            .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT)
            .withEnv("HAKJ_DEFAULT_DB", database);
    hakjdbContainer.start();
    Integer mappedPort = hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
    String host = hakjdbContainer.getHost();

    ClientConfig config = ClientConfig.builder().defaultDatabase(database).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);
    String key = "key2";
    String value = "Hello?";
    hakjdb.set(key, value);
    String result = hakjdb.get(key);
    assertEquals(value, result);
    hakjdb.disconnectNow();
  }

  @Test
  public void setStringDatabaseNotFoundShouldThrow() {
    String database = "test_db";
    Integer mappedPort = sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
    String host = sharedHakjdbContainer.getHost();
    ClientConfig config = ClientConfig.builder().defaultDatabase(database).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);
    assertThrows(HakjDBRequestException.class, () -> hakjdb.set("key3", "value"));
    hakjdb.disconnectNow();
  }

  @Test
  public void getStringObtainNewAuthTokenAfterExpire() {
    Integer mappedPort =
        authTokenExpireHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
    String host = authTokenExpireHakjdbContainer.getHost();
    ClientConfig config = ClientConfig.builder().usePassword(true).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);

    assertDoesNotThrow(
        () -> {
          Thread.sleep(1100);
          hakjdb.get("key4");
          hakjdb.disconnectNow();
        });
  }

  @Test
  public void setStringObtainNewAuthTokenAfterExpire() {
    Integer mappedPort =
        authTokenExpireHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
    String host = authTokenExpireHakjdbContainer.getHost();
    ClientConfig config = ClientConfig.builder().usePassword(true).build();
    HakjDB hakjdb = new HakjDB(host, mappedPort, config);

    assertDoesNotThrow(
        () -> {
          Thread.sleep(1100);
          hakjdb.set("key5", "value");
          hakjdb.disconnectNow();
        });
  }
}
