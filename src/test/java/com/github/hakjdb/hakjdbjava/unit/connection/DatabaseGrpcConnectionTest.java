package com.github.hakjdb.hakjdbjava.unit.connection;

import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.GrpcConnection;
import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DatabaseGrpcConnectionTest {
  @Mock private GrpcClient grpcClient;
  private GrpcConnection grpcConnection;

  @BeforeEach
  void setup() {
    grpcConnection = new GrpcConnection(grpcClient);
  }

  @Test
  public void sendRequestCreateDatabase() {
    String dbName = "test_db";
    String dbDescription = "Test desc 123.";
    when(grpcClient.callCreateDatabase(dbName, dbDescription)).thenReturn(dbName);
    String createdDbName = grpcConnection.sendRequestCreateDatabase(dbName, dbDescription);
    assertEquals(dbName, createdDbName);
    verify(grpcClient).callCreateDatabase(dbName, dbDescription);
  }

  @Test
  public void sendRequestGetDatabases() {
    Set<String> expectedDbNames =
        new HashSet<>(Collections.singletonList(UUID.randomUUID().toString()));
    when(grpcClient.callGetDatabases()).thenReturn(expectedDbNames);
    Set<String> actualDbNames = grpcConnection.sendRequestGetDatabases();
    assertEquals(expectedDbNames, actualDbNames);
    verify(grpcClient).callGetDatabases();
  }

  @Test
  public void sendRequestGetDatabaseInfo() {
    assertDoesNotThrow(
        () -> {
          String dbName = "test_db";
          String expectedJSON = "{}";
          when(grpcClient.callGetDatabaseInfo(dbName)).thenReturn(expectedJSON);
          String actualJSON = grpcConnection.sendRequestGetDatabaseInfo(dbName);
          assertEquals(expectedJSON, actualJSON);
          verify(grpcClient).callGetDatabaseInfo(dbName);
        });
  }

  @Test
  public void sendRequestDeleteDatabase() {
    String dbName = "test_db";
    when(grpcClient.callDeleteDatabase(dbName)).thenReturn(dbName);
    String deletedDbName = grpcConnection.sendRequestDeleteDatabase(dbName);
    assertEquals(dbName, deletedDbName);
    verify(grpcClient).callDeleteDatabase(dbName);
  }

  @Test
  public void sendRequestChangeDatabase() {
    String dbName = "test_db";
    String newName = "test_db_new";
    String newDescription = "Desc 123.";
    ChangeDatabaseOptions options =
        ChangeDatabaseOptions.builder()
            .setNewName(newName)
            .setNewDescription(newDescription)
            .build();
    when(grpcClient.callChangeDatabase(dbName, options)).thenReturn(newName);
    String changedDbName = grpcConnection.sendRequestChangeDatabase(dbName, options);
    assertEquals(newName, changedDbName);
    verify(grpcClient).callChangeDatabase(dbName, options);
  }
}
