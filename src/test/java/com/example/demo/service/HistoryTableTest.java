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
    public void testPut() throws Exception {
        Long timestamp = historyTable.put("foo", "bar");
        assertTrue((Long) 0L < timestamp);
    }

    @Test(expected = Exception.class)
    public void testPutInvalidInput() throws Exception {
        historyTable.put("foo", null);
    }

    @Test(expected = Exception.class)
    public void testPutEmptyInput() throws Exception {
        historyTable.put("", "");
    }

    @Test
    public void testGet() {
        String value = historyTable.get("foo", 1L);
        assertEquals("bar", value);
    }
}
