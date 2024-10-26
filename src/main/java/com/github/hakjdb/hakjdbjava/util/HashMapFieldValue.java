package com.github.hakjdb.hakjdbjava.util;

public class HashMapFieldValue {
    private final String value;
    private final boolean exists;

    public HashMapFieldValue(String value, boolean exists) {
        this.value = value;
        this.exists = exists;
    }

    public String getValue() {
        return value;
    }

    public boolean isExists() {
        return exists;
    }

    @Override
    public String toString() {
        return "HashMapFieldValue{" +
                "value='" + value + '\'' +
                ", exists=" + exists +
                '}';
    }
}