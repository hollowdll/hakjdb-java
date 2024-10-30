package com.github.hakjdb.hakjdbjava;

public interface StringKeyValueRequests {
    /**
     * Sets a key to hold a String value.
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * Returns the value of a String key.
     *
     * @param key
     * @return The value of the String key
     */
    String get(String key);
}
