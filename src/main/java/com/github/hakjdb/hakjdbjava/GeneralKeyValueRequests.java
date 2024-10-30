package com.github.hakjdb.hakjdbjava;

import java.util.Set;

public interface GeneralKeyValueRequests {
    /**
     * Returns all the keys.
     *
     * @return A list of keys
     */
    Set<String> getKeys();

    /**
     * Returns the data type of the value a key is holding.
     *
     * @param key
     * @return String representation of the type
     */
    String getKeyType(String key);

    /**
     * Deletes the specified keys.
     *
     * @param keys
     * @return The number of keys that were deleted
     */
    long deleteKeys(String... keys);

    /**
     * Deletes all the keys.
     */
    void deleteAllKeys();
}
