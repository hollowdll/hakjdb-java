package com.github.hakjdb.hakjdbjava.requests;

public interface StringKeyValueRequestSender {
    /**
     * Sends set request.
     *
     * @param key Key to use
     * @param value Value to store
     */
    void sendRequestSet(String key, String value);

    /**
     * Sends get request.
     *
     * @param key Key to use
     * @return Retrieved value or null if key doesn't exist
     */
    String sendRequestGet(String key);
}
