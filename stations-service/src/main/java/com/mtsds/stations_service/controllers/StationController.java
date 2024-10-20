package com.mtsds.stations_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;



@RestController
public class StationController {

    @GetMapping("/")
    public ResponseEntity<String> landing() throws IOException, InterruptedException {
        TestCPAPI testCPAPI = new TestCPAPI();
//        return ResponseEntity.status(HttpStatus.OK).body(teste123.Test());
        return ResponseEntity.status(HttpStatus.OK).body(testCPAPI.Test());
    }
}

//chamada a cp
