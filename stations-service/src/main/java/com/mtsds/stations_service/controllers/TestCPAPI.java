package com.mtsds.stations_service.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject;

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
                .uri(URI.create("https://api.cp.pt/cp-api/siv/trains"))
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

    public void GetTrips() throws IOException, InterruptedException {
        if (this.Token == null) {
            GetToken();
        }
        //Chamada para receber uma viagem do ponto A a B no dia X(pontos forcados mas no futuro nao)
        // 94-1008 sao bento
        // 94-29157 braga
        //A e B tem de ser o id que vem do json (na classe Station o Code penso eu)
//        String TripString = "{\"departureStationCode\": \"94-1008\",\"arrivalStationCode\": 94-29157,\"classes\": [1, 2],\"searchType\": 3,\"travelDate\": 2024-11-19,\"returnDate\": null,\"timeLimit\": null}";
        HttpClient client = HttpClient.newHttpClient();
        String TripString = "{\"departureStationCode\": \"94-1008\",\"arrivalStationCode\":\"94-29157\",\"travelDate\":\"2024-11-20\"}";
        HttpRequest tripRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/siv/travel/search"))
                .header("Authorization", "Bearer " + Token)
                .POST(HttpRequest.BodyPublishers.ofString(TripString))
                .build();
        HttpResponse<String> tripResponse = client.send(tripRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(tripResponse.body());
    }
}

//JSONObject
