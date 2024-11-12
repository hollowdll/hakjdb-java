package com.github.hakjdb.hakjdbjava.requests;

import com.github.hakjdb.hakjdbjava.util.HashMapFieldValue;

import java.util.Map;

public interface HashMapKeyValueRequests {
  /**
   * Sets the specified fields and their values in the HashMap stored at a key.
   *
   * @param key
   * @param hashMap field-value pairs to set
   * @return The number of fields that were added
   */
  long setHashMap(String key, Map<String, String> hashMap);

  /**
   * Returns all the field-value pairs of the HashMap stored at a key.
   *
   * @param key
   * @return The retrieved field-value pairs
   */
  Map<String, String> getHashMap(String key);

  /**
   * Returns the values of the specified fields in the HashMap stored at a key.
   *
   * @param key
   * @param fields
   * @return The retrieved field-value pairs
   */
  Map<String, HashMapFieldValue> getHashMapFieldValues(String key, String... fields);

  /**
   * Removes the specified fields from the HashMap stored at a key.
   *
   * @param key
   * @param fields
   * @return The number of fields that were removed
   */
  long deleteHashMapFields(String key, String... fields);
}
