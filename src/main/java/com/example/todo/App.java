package com.example.ipgeo;

import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;

public class App {

    static class IPResponse {
        String query;
        String country;
        String regionName;
        String city;
        String isp;
        double lat;
        double lon;
        String status;
        String message;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter IP address (leave blank for your own): ");
        String ip = scanner.nextLine().trim();

        if (ip.isEmpty()) {
            ip = ""; // Auto-detect
        }

        String apiUrl = "http://ip-api.com/json/" + ip;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        IPResponse data = gson.fromJson(response.body(), IPResponse.class);

        if ("success".equalsIgnoreCase(data.status)) {
            System.out.println("\n📍 IP Information:");
            System.out.println("IP: " + data.query);
            System.out.println("Country: " + data.country);
            System.out.println("Region: " + data.regionName);
            System.out.println("City: " + data.city);
            System.out.println("ISP: " + data.isp);
            System.out.println("Latitude: " + data.lat);
            System.out.println("Longitude: " + data.lon);
        } else {
            System.out.println("❌ Failed to fetch info: " + data.message);
        }
    }
}
