package com.example.demo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Hashtable;
import java.util.Map;

public class HistoryTable {

    Time time = new Time();

    public HistoryTable() {
    }

    public HistoryTable(Time time) {
        this.time = time;
    }

    /*Map within a Map
    The outer Map stores key and inner Map pair.
    The inner Map stores time and value pair.
    Example:
    KEY     | VALUE
            | KEY   | VALUE
    pet     | 7:00  | dog
            | 9:00  | cat
            | 10:20 | mouse

    food    | 8:10  | burger
            | 9:00  | ramen
            | 10:00 | pizza

    */
    Map<String, Map<Long, String>> historyTable = new Hashtable<>();

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
        Map<Long, String> timeValuePair = new Hashtable<>();

        if(!historyTable.containsKey(key)){
            timeValuePair.put(timestamp, value);
            historyTable.put(key, timeValuePair);
        }
        else{
            timeValuePair = historyTable.get(key);
            timeValuePair.put(timestamp, value);
            historyTable.put(key, timeValuePair);
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
    public String get(String key, Long timestamp) {
        return "bar";
    }

    /* Check for null or empty values */
    private boolean isValidInput(String key, String value){
        return (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value));
    }

}
