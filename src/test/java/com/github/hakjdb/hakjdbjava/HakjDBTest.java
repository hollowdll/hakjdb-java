package com.github.hakjdb.hakjdbjava;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HakjDBTest {
    @Test
    public void doesLoad() {
        HakjDB hakjdb = new HakjDB("localhost", 12345);
        assertTrue(hakjdb != null);
    }
}
