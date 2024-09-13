package org.main.unimap_pc.client.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class RestClientConnection {

    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    private RestClientConnection() {
    }

    public static boolean checkConnection(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            return response.statusCode() == 200;

        } catch (IOException e) {
            // TO DO logs
            System.err.println("IO Error while checking connection: " + e.getMessage());
        } catch (InterruptedException e) {
            // TO DO logs
            System.err.println("Interrupted while checking connection: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
