package com.example.HATEOASTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FastTest {
    @Test
    public void fastTest() throws InterruptedException {
        assertTrue(true);
    }

    @Test
    public void fiveSecondTest() throws InterruptedException {
        Thread.sleep(5000);
    }
}
