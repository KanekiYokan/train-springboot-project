package com.mtsds.stations_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        TestCPAPI testCPAPI = new TestCPAPI();
//        return ResponseEntity.status(HttpStatus.OK).body(teste123.Test());
        System.out.println(testCPAPI.Test());
    }
}
