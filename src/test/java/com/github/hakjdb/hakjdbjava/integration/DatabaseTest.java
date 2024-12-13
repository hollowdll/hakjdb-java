package com.github.hakjdb.hakjdbjava.integration;

import com.github.hakjdb.hakjdbjava.HakjDB;
import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;
import com.github.hakjdb.hakjdbjava.testutil.ContainerFactory;
import com.github.hakjdb.hakjdbjava.testutil.TestDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

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
    assertDoesNotThrow(
        () -> {
          Integer mappedPort =
              sharedHakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
          String host = sharedHakjdbContainer.getHost();
          HakjDB hakjdb = new HakjDB(host, mappedPort);
          String dbName = "createDatabase";
          String dbDescription = "Desc 123.";
          String createdDbName = hakjdb.createDatabase(dbName, dbDescription);
          assertEquals(dbName, createdDbName);

          String dbInfoJSON = hakjdb.getDatabaseInfo(dbName);
          ObjectMapper objectMapper = new ObjectMapper();
          JsonNode jsonNode = objectMapper.readTree(dbInfoJSON);
          assertTrue(jsonNode.has("name"));
          assertEquals(dbName, jsonNode.get("name").asText());
          assertTrue(jsonNode.has("description"));
          assertEquals(dbDescription, jsonNode.get("description").asText());
          hakjdb.disconnectNow();
        });
  }

  @Test
  public void getDatabases() {
    assertDoesNotThrow(
        () -> {
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
                  .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
          String host = hakjdbContainer.getHost();

          HakjDB hakjdb = new HakjDB(host, mappedPort);
          hakjdb.createDatabase("db1", "");
          hakjdb.createDatabase("db2", "");
          Set<String> dbNames = hakjdb.getDatabases();
          assertEquals(3, dbNames.size());
          assertTrue(dbNames.contains("db1"));
          assertTrue(dbNames.contains("db2"));
          assertTrue(dbNames.contains("default"));
          hakjdb.disconnectNow();
        });
  }

  @Test
  public void deleteDatabase() {
    assertDoesNotThrow(
        () -> {
          String dbName = "db1";
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
                  .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
          String host = hakjdbContainer.getHost();

          HakjDB hakjdb = new HakjDB(host, mappedPort);
          hakjdb.createDatabase(dbName, "");
          assertTrue(hakjdb.getDatabases().contains(dbName));
          String deletedDbName = hakjdb.deleteDatabase(dbName);
          assertEquals(dbName, deletedDbName);
          assertFalse(hakjdb.getDatabases().contains(dbName));
        });
  }

  @Test
  public void changeDatabase() {
    assertDoesNotThrow(
        () -> {
          String dbName = "db1";
          String dbDescription = "db1 desc";
          String newDbName = "db1-new";
          String newDbDescription = "db1 new desc";
          ChangeDatabaseOptions options =
              ChangeDatabaseOptions.builder()
                  .setNewName(newDbName)
                  .setNewDescription(newDbDescription)
                  .build();
          GenericContainer<?> hakjdbContainer =
              new GenericContainer<>(TestDefaults.HAKJDB_IMAGE)
                  .withExposedPorts(TestDefaults.HAKJDB_CONTAINER_PORT);
          hakjdbContainer.start();
          Integer mappedPort = hakjdbContainer.getMappedPort(TestDefaults.HAKJDB_CONTAINER_PORT);
          String host = hakjdbContainer.getHost();

          HakjDB hakjdb = new HakjDB(host, mappedPort);
          hakjdb.createDatabase(dbName, dbDescription);
          assertTrue(hakjdb.getDatabases().contains(dbName));
          String changedDbName = hakjdb.changeDatabase(dbName, options);
          assertEquals(newDbName, changedDbName);

          String dbInfoJSON = hakjdb.getDatabaseInfo(changedDbName);
          ObjectMapper objectMapper = new ObjectMapper();
          JsonNode jsonNode = objectMapper.readTree(dbInfoJSON);
          assertTrue(jsonNode.has("name"));
          assertEquals(newDbName, jsonNode.get("name").asText());
          assertTrue(jsonNode.has("description"));
          assertEquals(newDbDescription, jsonNode.get("description").asText());
          hakjdb.disconnectNow();
        });
  }
}
