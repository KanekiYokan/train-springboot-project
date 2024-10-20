package com.mtsds.stations_service.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private String type;
    private String id;
    private int uicId;
    private String name;
    private String timezone;
    private String country;
    private static final int passengers = 60;
}