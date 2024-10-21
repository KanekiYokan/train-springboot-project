package com.mtsds.stations_service.controllers;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RestController
public class StationController {
    TestCPAPI testCPAPI = new TestCPAPI();
    @GetMapping("/")
    public ResponseEntity<String> landing() throws IOException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body("Station Controller is online.");
    }

    @GetMapping("/stations")
    public ResponseEntity<String> getStations() throws IOException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(testCPAPI.GetStations());
    }
    @GetMapping("/stations/{station}")
    public ResponseEntity<String> getStation(@PathVariable String station) throws IOException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(testCPAPI.GetStopover(station));
    }
    @PostMapping("/journeys")
    public ResponseEntity<String> getJourneys(@RequestBody Map<String,String> json) throws IOException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(testCPAPI.GetJourneys(json));
    }
}

//chamada a cp
