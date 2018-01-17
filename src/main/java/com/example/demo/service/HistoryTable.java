package com.example.demo.service;

public class HistoryTable {

    Time time = new Time();

    public HistoryTable() {
    }

    public HistoryTable(Time time) {
        this.time = time;
    }

    /**
     * TODO: implement
     *
     * @param key Name to be used to recall the corresponding value.
     * @param value Value to be recalled.
     * @return Timestamp at which value was recorded.
     */
    public Long put(String key, String value) {
        return time.getCurrent();
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
}
