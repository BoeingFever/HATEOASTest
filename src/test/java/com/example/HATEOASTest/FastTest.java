package com.example.HATEOASTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class FastTest {
    @Test
    @DisplayName("Just assert true nothing else")
    public void fastTest() throws InterruptedException {
        //Only if the assumeTrue method returns true the test will run
        //Other assume xxx method works similarly
        assumeTrue(false);
        assertTrue(true);
    }

    @Test
    @DisplayName("just thread sleep 2 sec, also try better visual name")
    public void waitTest() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void addTest() throws InterruptedException {
        BigDecimal expected = new BigDecimal(60);
        BigDecimal result = new BigDecimal(20).add(new BigDecimal(30));
        assertEquals(expected,result);
    }
}
