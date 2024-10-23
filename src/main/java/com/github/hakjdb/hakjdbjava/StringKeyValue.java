package com.github.hakjdb.hakjdbjava;

public interface StringKeyValue {
    /**
     * Sets a key to hold a String value.
     *
     * @param key
     * @param value
     * @return nothing
     */
    void set(String key, String value);

    /**
     * Retrieves the value of a String key.
     *
     * @param key
     * @return The value of the String key
     */
    String get(String key);
}