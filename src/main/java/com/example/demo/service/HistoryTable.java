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
    The outer Map stores key and List of time and value pairs.
    Example:
    KEY     | VALUE
            |
    pet     | [(7:00  | dog),
            |  (9:00  | cat),
            |  (10:20 | mouse)]

    food    | [(8:10  | burger),
            | (9:00  | ramen),
            | (10:00 | pizza)

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

            // Check for duplicate entries inside list.
            for(TimeValuePair timeValuePair : timeValuePairList){
                if(isEqual(timeValuePair,newTimeValuePair))
                    return timeValuePair.timestamp;
            }

            timeValuePairList.add(newTimeValuePair);
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
        if(!isValidInput(key, timestamp))
            throw new Exception(String.format("Invalid input: key: %s timestamp: %d", key, timestamp));

        if(!historyTable.containsKey(key)){
            throw new Exception(String.format("Record not found in history. Key: %s", key), new Throwable("not_found"));
        }

        String result = "";
        ArrayList<TimeValuePair> timeValuePairList = historyTable.get(key);

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

    private boolean isValidInput(String key, Long timestamp){
        return (!StringUtils.isEmpty(key) && timestamp != null && timestamp > (Long) 0L);
    }

    private boolean isEqual(TimeValuePair timeValuePair, TimeValuePair newTimeValuePair){
        return (timeValuePair.timestamp.equals(newTimeValuePair.timestamp) &&
                timeValuePair.value.equals(newTimeValuePair.value));
    }

}
