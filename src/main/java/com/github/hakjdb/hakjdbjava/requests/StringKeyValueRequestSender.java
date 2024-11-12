package com.github.hakjdb.hakjdbjava.requests;

public interface StringKeyValueRequestSender {
    /**
     * Send set request.
     *
     * @param key Key to use
     * @param value Value to store
     */
    void sendRequestSet(String key, String value);

    /**
     * Send get request.
     *
     * @param key Key to use
     * @return Retrieved value
     */
    String sendRequestGet(String key);
}
