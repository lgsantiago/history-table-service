package com.example.demo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class HistoryTable {

    Time time = new Time();

    public HistoryTable() {
    }

    public HistoryTable(Time time) {
        this.time = time;
    }

    /*List within a Map
    The Map stores keys and a list of time and value pair objects.
    Example:
    KEY     | VALUE
            |
    pet     | [(7:00  | dog),
            |  (9:00  | cat),
            |  (10:20 | mouse)]

    food    | [(8:10  | burger),
            | (9:00  | ramen),
            | (10:00 | pizza)]

    */
    Map<String, ArrayList<TimeValuePair>> historyTable = new Hashtable<>();

    /**
     * TODO: implement
     *
     * @param key Name to be used to recall the corresponding value.
     * @param value Value to be recalled.
     * @return Timestamp at which value was recorded.
     */
    public Long put(String key, String value) throws Exception{
        // Check input
        if(!isValidInput(key, value)) {
            throw new Exception(String.format("Invalid input: key: %s, value %s", key, value));
        }

        Long timestamp = time.getCurrent();
        TimeValuePair newTimeValuePair = new TimeValuePair(timestamp, value);
        ArrayList<TimeValuePair> timeValuePairList = new ArrayList<>();

        if(!historyTable.containsKey(key)){
            timeValuePairList.add(newTimeValuePair);
            historyTable.put(key, timeValuePairList);
        }
        else{
            timeValuePairList = historyTable.get(key);

            /* Extract last element of existing list
            * and check if new timestamp is less than
            * the last element timestamp to check for
            * potential duplicates. Otherwise, skip
            * iterating through list.*/
            TimeValuePair lastElement = timeValuePairList.get(timeValuePairList.size()-1);

            if(newTimeValuePair.timestamp <= lastElement.timestamp) {
                // Check for duplicates. Binary search approach.
                Long duplicateTimestamp = searchDuplicate(timeValuePairList, newTimeValuePair);

                if (duplicateTimestamp >= 0L)
                    return duplicateTimestamp;

                // Duplicate search: Linear approach
//                for (TimeValuePair timeValuePair : timeValuePairList) {
//                    if (isEqual(timeValuePair, newTimeValuePair))
//                        return timeValuePair.timestamp;
//                }
            }

            timeValuePairList.add(newTimeValuePair);
            historyTable.put(key, timeValuePairList);
        }

        return timestamp;
    }

    /**
     * TODO: implement
     *
     * @param key Name to be used to recall the corresponding value.
     * @param timestamp Time at which the value of this key is to be returned.
     * @return Value associated with the key at the given timestamp.
     */
    public String get(String key, Long timestamp) throws Exception {
        String result = "";

        if(!isValidInput(key, timestamp))
            throw new Exception(String.format("Invalid input: key: %s timestamp: %d", key, timestamp));

        if(!historyTable.containsKey(key))
            throw new Exception(String.format("Record not found in history table. Key: %s", key), new Throwable("not_found"));

        ArrayList<TimeValuePair> timeValuePairList = historyTable.get(key);

        if(timeValuePairList.isEmpty())
            throw new Exception(String.format("History not found. Key: %s", key), new Throwable("not_found"));

        /* If timestamp requested is less than
        * first element's timestamp then return
        * empty value. */
        if(timestamp < timeValuePairList.get(0).timestamp)
            return result;

        /* If timestamp requested is greater than
        * or equal than last element's timestamp then return
        * its value.*/
        if(timestamp >= timeValuePairList.get(timeValuePairList.size()-1).timestamp)
            return timeValuePairList.get(timeValuePairList.size()-1).value;

        // Search for value
        for(TimeValuePair timeValuePair : timeValuePairList){
            /*
            The condition statement below increases performance
            when the time value pair list is too long. This eliminates
            the need to iterate the entire list.
            */
            if(timeValuePair.timestamp.equals(timestamp))
                return timeValuePair.value;

            if(timeValuePair.timestamp <= timestamp)
                result = timeValuePair.value;
        }

        return result;
    }

    /* getB contains my initial approach on the search portion. I kept this
    method to run a performance test against the get method above.
    See testGetPerformance() inside HistoryTable class.*/
    public String getB(String key, Long timestamp) throws Exception {
        String result = "";

        if(!isValidInput(key, timestamp))
            throw new Exception(String.format("Invalid input: key: %s timestamp: %d", key, timestamp));

        if(!historyTable.containsKey(key))
            throw new Exception(String.format("Record not found in history table. Key: %s", key), new Throwable("not_found"));

        ArrayList<TimeValuePair> timeValuePairList = historyTable.get(key);

        if(timeValuePairList.isEmpty())
            throw new Exception(String.format("History not found. Key: %s", key), new Throwable("not_found"));

        /* If timestamp requested is less than
        * first element's timestamp then return
        * empty value. */
        if(timestamp < timeValuePairList.get(0).timestamp)
            return result;

        if(timestamp >= timeValuePairList.get(timeValuePairList.size()-1).timestamp)
            return timeValuePairList.get(timeValuePairList.size()-1).value;

        // Search for value
        for(TimeValuePair timeValuePair : timeValuePairList){
            if(timeValuePair.timestamp <= timestamp)
                result = timeValuePair.value;
        }

        return result;
    }

    /* Check for null or empty values */
    private boolean isValidInput(String key, String value){
        return (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value));
    }

    /* Check for null, empty values, or negative timestamps */
    private boolean isValidInput(String key, Long timestamp){
        return (!StringUtils.isEmpty(key) && timestamp != null && timestamp > (Long) 0L);
    }

    private boolean isEqual(TimeValuePair timeValuePair, TimeValuePair newTimeValuePair){
        return (timeValuePair.timestamp.equals(newTimeValuePair.timestamp) &&
                timeValuePair.value.equals(newTimeValuePair.value));
    }

    /* Binary search on duplicates in case
     * of race condition where two clients
     * attempt to insert the same value with
     * equal timestamp.*/
    private Long searchDuplicate(ArrayList<TimeValuePair> timeValuePairList, TimeValuePair targetTimeValuePair){
        int start = 0;
        int end = timeValuePairList.size()-1;

        while(start <= end){
            int mid = (start + end) / 2;

            if(isEqual(timeValuePairList.get(mid), targetTimeValuePair))
                return timeValuePairList.get(mid).timestamp;

            if(targetTimeValuePair.timestamp < timeValuePairList.get(mid).timestamp)
                end = mid-1;
            else
                start = mid + 1;

        }

        return -1L;
    }
}
