package com.mtsds.stations_service.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject;
import java.util.Map;

public class TestCPAPI {
    String Token;

    public void GetToken() throws IOException, InterruptedException {
        // Corrected request body for x-www-form-urlencoded
        //Chamada para receber token
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest tokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/oauth/token"))
                .header("Authorization", "Basic Y3AtbW9iaWxlOnBhc3M=")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();
        //Tratamento do token
        HttpResponse<String> tokenResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = tokenResponse.body();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        this.Token = jsonObject.getString("access_token");
    }

    public String GetStations() throws IOException, InterruptedException {
        if (this.Token == null) {
            GetToken();
        }
        //Chamada para receber estacoes
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest stationRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/siv/stations"))
                .header("Authorization", "Bearer " + Token)
                .build();
        HttpResponse<String> stationResponse = client.send(stationRequest, HttpResponse.BodyHandlers.ofString());

        return stationResponse.body();
        //Tratamento das estacoes (atencao que e preciso haver exatamente os mesmo campos que vem da chamada da api :shrug:)
        //mudar return para Station[]
//        ObjectMapper objectMapper = new ObjectMapper();
//            Station[] stationArray = objectMapper.readValue(stationResponse.body(), Station[].class);
//            return stationArray;
    }
    public String GetStopover(String station) throws IOException, InterruptedException {
        if (this.Token == null) {
            GetToken();
        }
        //Chamada para receber estacoes
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest stopoverRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/siv/stations/"+station+"/timetable/"+"2024-11-21")) //TODO alterar fixed date
                .header("Authorization", "Bearer " + Token)
                .build();
        HttpResponse<String> stopoverResponse = client.send(stopoverRequest, HttpResponse.BodyHandlers.ofString());

        return stopoverResponse.body();
        //Tratamento das estacoes (atencao que e preciso haver exatamente os mesmo campos que vem da chamada da api :shrug:)
        //mudar return para Station[]
//        ObjectMapper objectMapper = new ObjectMapper();
//            Station[] stationArray = objectMapper.readValue(stationResponse.body(), Station[].class);
//            return stationArray;
    }

    public String GetJourneys(Map<String,String> json) throws IOException, InterruptedException {
        if (this.Token == null) {
            GetToken();
        }
        String StartStation = json.get("startStation");
        String EndStation =json.get("endStation");
            String Date = json.get("date");
        if (Date == null){
            System.out.println(java.time.LocalDate.now().toString());
            Date = java.time.LocalDate.now().toString();
        }
        //Chamada para receber uma viagem do ponto A a B no dia X(pontos forcados mas no futuro nao)
        // 94-1008 sao bento
        // 94-2006 campanha
        // 94-29157 braga
        //A e B tem de ser o id que vem do json (na classe Station o Code penso eu)
//        String TripString = "{\"departureStationCode\": \"94-1008\",\"arrivalStationCode\": 94-29157,\"classes\": [1, 2],\"searchType\": 3,\"travelDate\": 2024-11-19,\"returnDate\": null,\"timeLimit\": null}";
        HttpClient client = HttpClient.newHttpClient();
        String JourneyString = "{\"departureStationCode\": \""+StartStation+"\",\"arrivalStationCode\":\""+EndStation+"\",\"travelDate\":\""+Date+"\"}";
        HttpRequest tripRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/siv/travel/search"))
                .header("Authorization", "Bearer " + Token)
                .POST(HttpRequest.BodyPublishers.ofString(JourneyString))
                .build();
        HttpResponse<String> journeyResponse = client.send(tripRequest, HttpResponse.BodyHandlers.ofString());
//        System.out.println(tripResponse.body());
        return journeyResponse.body();
    }
}

//JSONObject
