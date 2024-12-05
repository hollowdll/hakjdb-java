package com.github.hakjdb.hakjdbjava.requests;

import com.github.hakjdb.hakjdbjava.params.ChangeDatabaseOptions;

import java.util.Set;

public interface DatabaseRequests {
  /**
   * Creates a new database with the specified name and description.
   *
   * @param dbName Name of the database
   * @param dbDescription Description of the database
   * @return Name of the created database
   */
  String createDatabase(String dbName, String dbDescription);

  /**
   * Returns the names of all the databases on the server.
   *
   * @return Database names
   */
  Set<String> getDatabases();

  /**
   * Returns information about a database as a JSON string.
   *
   * @param dbName Name of the database
   * @return JSON string of database information
   */
  String getDatabaseInfo(String dbName);

  /**
   * Deletes the specified database.
   *
   * @param dbName Name of the database
   * @return Name of the deleted database
   */
  String deleteDatabase(String dbName);

  /**
   * Changes the metadata of a database.
   *
   * @param dbName Name of the database
   * @param options Options of what to change
   * @return Name of the changed database
   */
  String changeDatabase(String dbName, ChangeDatabaseOptions options);
}
