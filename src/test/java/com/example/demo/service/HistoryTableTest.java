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
    public void testPutMultipleRecords() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();
        Long timestamp  = historyTable.put("hospital", "mount");
        Long timestamp2 = historyTable.put("hospital", "kaiser");
        Long timestamp3 = historyTable.put("hospital", "sacred");
        assertTrue((Long) 0L < timestamp);
        assertTrue(historyTable.historyTable.get("hospital").size() == 3);
        assertEquals("kaiser", historyTable.historyTable.get("hospital").get(1).value);
        assertEquals(timestamp2, historyTable.historyTable.get("hospital").get(1).timestamp);
        assertEquals(timestamp3, historyTable.historyTable.get("hospital").get(2).timestamp);
    }

    @Test
    public void testPutDuplicatesSameTime() throws Exception{
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
    public void testPutOneDuplicate() throws Exception{
        Mockito.doCallRealMethod().when(time).getCurrent();
        historyTable.put("car", "original1");
        historyTable.put("car", "original2");
        Long original3 = historyTable.put("car", "original3");
        historyTable.put("car", "original4");

        // Duplicate of original3
        Mockito.doReturn(original3).when(time).getCurrent();
        historyTable.put("car", "original3");

        Assert.assertEquals(4, historyTable.historyTable.get("car").size());
    }

    @Test
    public void testPutDuplicateDifferentTime() throws Exception{
        Mockito.doReturn(1516427008446L).when(time).getCurrent();
        historyTable.put("dup", "duplicate");
        Mockito.doReturn(1516427008450L).when(time).getCurrent();
        historyTable.put("dup", "duplicate");
        Assert.assertEquals(2, historyTable.historyTable.get("dup").size());
    }

    @Test
    public void testPutNonDuplicateSameTime() throws Exception{
        Mockito.doReturn( 1516427008446L).when(time).getCurrent();
        historyTable.put("city", "miami");
        historyTable.put("city", "london");
        historyTable.put("city", "san francisco");
        Assert.assertEquals(3, historyTable.historyTable.get("city").size());
        Assert.assertEquals("san francisco", historyTable.get("city", 1516427008446L));
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
    public void testGet() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();

        Long timestampBurger = historyTable.put("food", "burger");

        String value = historyTable.get("food", timestampBurger);
        assertEquals("burger", value);
    }

    @Test
    public void testGetPastMoment() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();
        historyTable.put("park", "alamo");
        historyTable.put("park", "golden gate");
        historyTable.put("park", "dolores");
        Thread.sleep(1000);

        assertEquals("dolores", historyTable.get("park", time.getCurrent()));
    }

    @Test
    public void testGetMultipleRecords() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();
        Long timestampEggs = historyTable.put("food", "eggs");
        Long timestampChicken = historyTable.put("food", "chicken");

        assertEquals("eggs", historyTable.get("food", timestampEggs));
        assertEquals("chicken", historyTable.get("food", timestampChicken));
    }

    @Test
    public void testGetMultipleTimestampInBetween() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();

        historyTable.put("restaurant", "kitchen story");

        Thread.sleep(100);
        Long timestampInBetween = time.getCurrent();
        Thread.sleep(100);

        historyTable.put("restaurant", "myriad");

        assertEquals("kitchen story", historyTable.get("restaurant", timestampInBetween));
    }

    @Test
    public void testGetEarlierTimestamp() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();

        Long timestamp = time.getCurrent();
        Thread.sleep(100);

        historyTable.put("name", "jessica");
        historyTable.put("name", "luis");
        historyTable.put("name", "jason");

        assertTrue(historyTable.get("name", timestamp).isEmpty());
    }

    @Test(expected = Exception.class)
    public void testGetNonExistent() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();

        Long timestamp = time.getCurrent();

        historyTable.get("nokey", timestamp);
    }

    @Test(expected = Exception.class)
    public void testGetInvalidInput() throws Exception {
        historyTable.get("foo", null);
    }

    @Test(expected = Exception.class)
    public void testGetEmptyInput() throws Exception {
        historyTable.get("", null);
    }

    @Test(expected = Exception.class)
    public void testGetInvalidTimestamp() throws Exception {
        historyTable.get("foo", -123L);
    }

    /* This is a performance test on methods
    * get and getB.
    *
    * These are the results after running a few tests
    * locally:
    *
    * 50 records:
    *  get A: 0.149897
    *  get B: 0.031214
    *
    * 100 records:
    *  get A: 0.071318
    *  get B: 0.071269
    *
    * 300 records:
    *  get A: 0.083242
    *  get B: 0.157169
    * */
    @Test
    @Ignore
    public void testGetPerformance() throws Exception {
        Mockito.doCallRealMethod().when(time).getCurrent();
        Long timestamp = 0L;

        for(int i = 0; i < 300; i++){
            String value = "test" + i;

            if(i == 10)
                timestamp = historyTable.put("performance", value);
            else
                historyTable.put("performance", value);

            Thread.sleep(100);
        }

        Long start = System.nanoTime();
        historyTable.get("performance", timestamp);
        Long end = System.nanoTime();

        Long startB = System.nanoTime();
        historyTable.getB("performance", timestamp);
        Long endB = System.nanoTime();

        System.out.println("Timestamp: "+ timestamp);
        System.out.println("Get A: "+ (end-start) / 1e6);
        System.out.println("Get B: "+ (endB-startB) / 1e6);
    }




}
