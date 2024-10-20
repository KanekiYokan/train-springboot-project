package com.mtsds.stations_service.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
//import org.json.j
import org.json.JSONObject;

public class TestCPAPI {

    public String Test() throws IOException, InterruptedException {
        // Corrected request body for x-www-form-urlencoded
        String requestBody = "grant_type=client_credentials";

        // Create HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Create HttpRequest with required headers and POST method
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/oauth/token")) // Replace with actual API URL
                .header("Authorization", "Basic Y3AtbW9iaWxlOnBhc3M=") // Replace with your actual base64 credentials
                .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // Pass the correct request body
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Return the response body
        //sacar o access token
        //libraria que vai a um json e saca o value da key que das
        //String token = fromjson(reponse.body(),'access_token')

        //https://api.cp.pt/cp-api/siv/stations
        JSONObject TokenRequest = new JSONObject(response.body());
        String token = TokenRequest.getString("access_token");
        HttpRequest Newrequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cp.pt/cp-api/siv/stations")) // Replace with actual API URL
                .header("Authorization", "Bearer " + token) // Replace with your actual base64 credentials
//                .header("Content-Type", "application/x-www-form-urlencoded") // Set the content type
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // Pass the correct request body
                .build();

        HttpResponse<String> newResponse = client.send(Newrequest, HttpResponse.BodyHandlers.ofString());

        return newResponse.body();

    }
}
//JSONObject
