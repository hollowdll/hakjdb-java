package com.github.hakjdb.hakjdbjava;

import java.util.Set;

public interface Database {
    /**
     * Creates a new database with the specified name.
     * Sets an empty database description.
     *
     * @param dbName
     * @return Name of the created database
     */
    String createDatabase(String dbName);

    /**
     * Creates a new database with the specified name and description.
     *
     * @param dbName
     * @param dbDescription
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
     * @param dbName
     * @return JSON string of database information
     */
    String getDatabaseInfo(String dbName);

    /**
     * Deletes the specified database.
     *
     * @param dbName
     * @return Name of the deleted database
     */
    String deleteDatabase(String dbName);

    /**
     * Changes the metadata of a database.
     *
     * @param dbName
     * @param newName Null does not change the name
     * @param newDescription Null does not change the description
     * @return Name of the changed database
     */
    String changeDatabase(String dbName, String newName, String newDescription);
}
