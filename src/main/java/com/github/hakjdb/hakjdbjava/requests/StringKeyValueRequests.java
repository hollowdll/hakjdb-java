package com.github.hakjdb.hakjdbjava.requests;

public interface StringKeyValueRequests {
  /**
   * Sets a key to hold a String value.
   *
   * @param key Key to use
   * @param value Value to store
   */
  void set(String key, String value);

  /**
   * Returns the value of a String key.
   *
   * @param key Key to use
   * @return Retrieved value
   */
  String get(String key);
}
