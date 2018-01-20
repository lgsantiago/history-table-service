package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO: extend tests
public class HistoryTableTest extends DemoApplicationTests {

    @InjectMocks
    private static HistoryTable historyTable;

    @Mock
    Time time;

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
        Mockito.doCallRealMethod().when(time).getCurrent();
        Long timestamp = historyTable.put("foo", "bar");
        assertTrue((Long) 0L < timestamp);
        assertTrue(historyTable.historyTable.get("foo").size() == 1);
        assertEquals("bar", historyTable.historyTable.get("foo").get(0).value);
        assertEquals(timestamp, historyTable.historyTable.get("foo").get(0).timestamp);
    }

    @Test
    public void testPutDuplicateSameTimeRecord() throws Exception{
        Long timestamp = new Long(0151642);
        Mockito.doReturn(timestamp).when(time).getCurrent();
        Long duplicate1 = historyTable.put("duplicate", "duplicate");
        Long duplicate2 = historyTable.put("duplicate", "duplicate");
        Long duplicate3 = historyTable.put("duplicate", "duplicate");
        Long duplicate4 = historyTable.put("duplicate", "duplicate");
        Assert.assertEquals(1, historyTable.historyTable.get("duplicate").size());
        Assert.assertEquals(timestamp, duplicate1);
        Assert.assertEquals(timestamp, duplicate2);
        Assert.assertEquals(timestamp, duplicate3);
        Assert.assertEquals(timestamp, duplicate4);
    }

    @Test
    public void testPutDuplicateDifferentTimeRecord() throws Exception{
        Mockito.doReturn(1516427008446L).when(time).getCurrent();
        historyTable.put("dup", "duplicate");
        Mockito.doReturn(1516427008450L).when(time).getCurrent();
        historyTable.put("dup", "duplicate");
        Assert.assertEquals(2, historyTable.historyTable.get("dup").size());
    }

    @Test
    public void testPutNonDuplicateSameTimeRecord() throws Exception{
        Mockito.doReturn((Long) 1516427008446L).when(time).getCurrent();
        historyTable.put("city", "miami");
        historyTable.put("city", "london");
        historyTable.put("city", "san francisco");
        Assert.assertEquals(3, historyTable.historyTable.get("city").size());
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
    @Ignore
    public void testGet() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();
        String value = historyTable.get("foo", 1L);
        assertEquals("bar", value);
    }
}
