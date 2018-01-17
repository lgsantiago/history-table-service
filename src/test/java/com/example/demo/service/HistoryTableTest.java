package com.example.demo.service;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO: extend tests
public class HistoryTableTest {

    private static HistoryTable historyTable;

    @BeforeClass
    public static void init() {
        historyTable = new HistoryTable();
    }

    /*
    The existing tests are merely samples.
    They do not describe the expected behaviour of a correct solution.
    Replace them to test your solution.
     */

    @Test
    public void testPut() {
        Long timestamp = historyTable.put("foo", "bar");
        assertTrue((Long) 0L < timestamp);
    }

    @Test
    public void testGet() {
        String value = historyTable.get("foo", 1L);
        assertEquals("bar", value);
    }
}
