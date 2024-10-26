package com.github.hakjdb.hakjdbjava;

import java.util.Set;

public interface GeneralKeyValue {
    /**
     * Retrieve all the keys.
     *
     * @return A list of keys
     */
    Set<String> getKeys();

    /**
     * Retrieve the data type of the value a key is holding.
     *
     * @param key
     * @return String representation of the type
     */
    String getKeyType(String key);

    /**
     * Delete the specified keys.
     *
     * @param keys
     * @return The number of keys that were deleted
     */
    long deleteKeys(String... keys);

    /**
     * Delete all the keys.
     */
    void deleteAllKeys();
}