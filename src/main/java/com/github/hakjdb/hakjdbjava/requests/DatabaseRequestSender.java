package com.github.hakjdb.hakjdbjava.requests;

import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;

import java.util.Set;

public interface DatabaseRequestSender {
  /**
   * Sends createDatabase request.
   *
   * @param dbName Name of the database
   * @param dbDescription Description of the database
   * @return Name of the created database
   */
  String sendRequestCreateDatabase(String dbName, String dbDescription);

  /**
   * Sends getDatabases request.
   *
   * @return Database names
   */
  Set<String> sendRequestGetDatabases();

  /**
   * Sends getDatabaseInfo request.
   *
   * @param dbName Name of the database
   * @return JSON string of database information
   */
  String sendRequestGetDatabaseInfo(String dbName);

  /**
   * Sends deleteDatabase request.
   *
   * @param dbName Name of the database
   * @return Name of the deleted database
   */
  String sendRequestDeleteDatabase(String dbName);

  /**
   * Sends changeDatabase request.
   *
   * @param dbName Name of the database
   * @param options Options of what to change
   * @return Name of the changed database
   */
  String sendRequestChangeDatabase(String dbName, ChangeDatabaseOptions options);
}
