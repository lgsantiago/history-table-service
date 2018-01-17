package com.example.demo.controller;

import com.example.demo.model.HistoryTableGetRequest;
import com.example.demo.model.HistoryTableGetResponse;
import com.example.demo.model.HistoryTablePutRequest;
import com.example.demo.model.HistoryTablePutResponse;
import com.example.demo.service.HistoryTable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryTableController {

    private HistoryTable historyTable;

    public HistoryTableController() {
        historyTable = new HistoryTable();
    }

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    @ResponseBody
    public HistoryTablePutResponse put(@RequestBody HistoryTablePutRequest request) {
        System.out.println("/put");
        System.out.println(String.format("key: %s", request.getKey()));
        System.out.println(String.format("value: %s", request.getValue()));

        Long timestamp = historyTable.put(request.getKey(), request.getKey());

        HistoryTablePutResponse response = new HistoryTablePutResponse();
        response.setTimestamp(timestamp);
        return response;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public HistoryTableGetResponse get(@RequestBody HistoryTableGetRequest request) {
        System.out.println("/get");
        System.out.println(request.getKey());
        System.out.println(request.getTimestamp());

        String value = historyTable.get(request.getKey(), request.getTimestamp());

        HistoryTableGetResponse response = new HistoryTableGetResponse();
        response.setValue(value);
        return response;
    }
}
