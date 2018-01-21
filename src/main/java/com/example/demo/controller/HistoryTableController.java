package com.example.demo.controller;

import com.example.demo.model.HistoryTableGetRequest;
import com.example.demo.model.HistoryTableGetResponse;
import com.example.demo.model.HistoryTablePutRequest;
import com.example.demo.model.HistoryTablePutResponse;
import com.example.demo.service.HistoryTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HistoryTablePutResponse> put(@RequestBody HistoryTablePutRequest request) {
        try {
            System.out.println("key: " + request.getKey());
            System.out.println("value: " + request.getValue());

            Long timestamp = historyTable.put(request.getKey(), request.getValue());

            System.out.println("Timestamp: " + timestamp);

            HistoryTablePutResponse response = new HistoryTablePutResponse();
            response.setTimestamp(timestamp);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(new HistoryTablePutResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<HistoryTableGetResponse> get(@RequestBody HistoryTableGetRequest request) {
        try {
            System.out.println("key: " + request.getKey());
            System.out.println("timestamp: " + request.getTimestamp());

            String value = historyTable.get(request.getKey(), request.getTimestamp());

            System.out.println("Value: " + value);

            HistoryTableGetResponse response = new HistoryTableGetResponse();
            response.setValue(value);

            if(StringUtils.isEmpty(response.getValue()))
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());

            if(exception.getCause() != null) {
                if (exception.getCause().getMessage().equals("not_found"))
                    return new ResponseEntity<>(new HistoryTableGetResponse(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new HistoryTableGetResponse(), HttpStatus.BAD_REQUEST);
        }
    }
}
