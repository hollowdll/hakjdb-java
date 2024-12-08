package com.github.hakjdb.hakjdbjava.unit.client;

import com.github.hakjdb.hakjdbjava.api.v1.dbpb.Db;
import com.github.hakjdb.hakjdbjava.grpc.DatabaseGrpcClient;
import com.github.hakjdb.hakjdbjava.grpc.GrpcClient;
import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;
import com.google.protobuf.util.JsonFormat;
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
public class DatabaseGrpcClientTest {
  @Mock private DatabaseGrpcClient databaseGrpcClient;
  private GrpcClient grpcClient;

  @BeforeEach
  void setup() {
    grpcClient = new GrpcClient(null, null, 0, "", null, null, null, databaseGrpcClient);
  }

  @Test
  public void callCreateDatabase() {
    String name = "test_db";
    String description = "Desc 123.";
    int timeout = grpcClient.getRequestTimeoutSeconds();
    Db.CreateDBRequest request =
        Db.CreateDBRequest.newBuilder().setDbName(name).setDescription(description).build();
    Db.CreateDBResponse response = Db.CreateDBResponse.newBuilder().setDbName(name).build();

    when(databaseGrpcClient.createDatabase(request, timeout)).thenReturn(response);
    String createdDbName = grpcClient.callCreateDatabase(name, description);
    assertEquals(name, createdDbName);
    verify(databaseGrpcClient).createDatabase(request, timeout);
  }

  @Test
  public void callGetDatabases() {
    String name = UUID.randomUUID().toString();
    int timeout = grpcClient.getRequestTimeoutSeconds();
    Db.GetAllDBsRequest request = Db.GetAllDBsRequest.newBuilder().build();
    Db.GetAllDBsResponse response = Db.GetAllDBsResponse.newBuilder().addDbNames(name).build();

    when(databaseGrpcClient.getDatabases(request, timeout)).thenReturn(response);
    Set<String> retrievedNames = grpcClient.callGetDatabases();
    assertTrue(retrievedNames.contains(name));
    assertEquals(1, retrievedNames.size());
    verify(databaseGrpcClient).getDatabases(request, timeout);
  }

  @Test
  public void callGetDatabaseInfo() {
    assertDoesNotThrow(
        () -> {
          String dbName = "test_db";
          int timeout = grpcClient.getRequestTimeoutSeconds();
          Db.GetDBInfoRequest request = Db.GetDBInfoRequest.newBuilder().setDbName(dbName).build();
          Db.GetDBInfoResponse response = Db.GetDBInfoResponse.newBuilder().build();
          String expectedJSON = JsonFormat.printer().print(response);

          when(databaseGrpcClient.getDatabaseInfo(request, timeout)).thenReturn(response);
          String actualJSON = grpcClient.callGetDatabaseInfo(dbName);
          assertEquals(expectedJSON, actualJSON);
          verify(databaseGrpcClient).getDatabaseInfo(request, timeout);
        });
  }

  @Test
  public void callDeleteDatabase() {
    String name = "test_db";
    int timeout = grpcClient.getRequestTimeoutSeconds();
    Db.DeleteDBRequest request = Db.DeleteDBRequest.newBuilder().setDbName(name).build();
    Db.DeleteDBResponse response = Db.DeleteDBResponse.newBuilder().setDbName(name).build();

    when(databaseGrpcClient.deleteDatabase(request, timeout)).thenReturn(response);
    String deletedDbName = grpcClient.callDeleteDatabase(name);
    assertEquals(name, deletedDbName);
    verify(databaseGrpcClient).deleteDatabase(request, timeout);
  }

  @Test
  public void callChangeDatabase() {
    String name = "test_db";
    String newName = "test_db_new";
    String newDescription = "Desc 123.";
    ChangeDatabaseOptions options =
        ChangeDatabaseOptions.builder()
            .setNewName(newName)
            .setNewDescription(newDescription)
            .build();
    int timeout = grpcClient.getRequestTimeoutSeconds();
    Db.ChangeDBRequest request =
        Db.ChangeDBRequest.newBuilder()
            .setDbName(name)
            .setChangeName(true)
            .setNewName(newName)
            .setChangeDescription(true)
            .setNewDescription(newDescription)
            .build();
    Db.ChangeDBResponse response = Db.ChangeDBResponse.newBuilder().setDbName(newName).build();

    when(databaseGrpcClient.changeDatabase(request, timeout)).thenReturn(response);
    String changedDbName = grpcClient.callChangeDatabase(name, options);
    assertEquals(newName, changedDbName);
    verify(databaseGrpcClient).changeDatabase(request, timeout);
  }

  @Test
  public void callChangeDatabaseWithNullValues() {
    String dbName = "test_db";
    ChangeDatabaseOptions options =
        ChangeDatabaseOptions.builder().setNewName(null).setNewDescription(null).build();
    int timeout = grpcClient.getRequestTimeoutSeconds();
    Db.ChangeDBRequest request =
        Db.ChangeDBRequest.newBuilder()
            .setDbName(dbName)
            .setChangeName(false)
            .setNewName("")
            .setChangeDescription(false)
            .setNewDescription("")
            .build();
    Db.ChangeDBResponse response = Db.ChangeDBResponse.newBuilder().setDbName(dbName).build();

    when(databaseGrpcClient.changeDatabase(request, timeout)).thenReturn(response);
    String changedDbName = grpcClient.callChangeDatabase(dbName, options);
    assertEquals(dbName, changedDbName);
    verify(databaseGrpcClient).changeDatabase(request, timeout);
  }
}
