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
public class DatabaseTest {
  @Container
  private static final GenericContainer<?> sharedHakjdbContainer =
      ContainerFactory.createDefaultHakjDBContainer();

  @BeforeAll
  static void setUp() {
    sharedHakjdbContainer.start();
  }

  @Test
  public void createDatabase() {
    assertDoesNotThrow(() -> {
      Integer mappedPort = sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
      String host = sharedHakjdbContainer.getHost();
      HakjDB hakjdb = new HakjDB(host, mappedPort);
      String dbName = "createDatabase";
      String dbDescription = "Desc 123.";
      String createdDbName = hakjdb.createDatabase(dbName, dbDescription);
      assertEquals(dbName, createdDbName);
      String dbInfo = hakjdb.getDatabaseInfo(dbName);
      //System.out.println(dbInfo);
      assertFalse(dbInfo.isEmpty());
      hakjdb.disconnectNow();
    });
  }

  @Test
  public void getDatabases() {
    fail("TODO");
  }

  @Test
  public void getDatabaseInfo() {
    fail("TODO");
  }

  @Test
  public void deleteDatabase() {
    fail("TODO");
  }

  @Test
  public void changeDatabase() {
    fail("TODO");
  }
}
