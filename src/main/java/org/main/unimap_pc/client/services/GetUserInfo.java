package org.main.unimap_pc.client.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import org.main.unimap_pc.client.models.UserModel;

public class GetUserInfo {
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static CompletableFuture<UserModel> getUserByEmailOrLogin(String url, String data) {
        if (data == null) {
            return CompletableFuture.completedFuture(null);
        }

        HttpRequest request;
        if (EMAIL_PATTERN.matcher(data).matches()) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "email/" + data))
                    .GET()
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "login/" + data))
                    .GET()
                    .build();
        }

        System.out.println("Requesting user info with request: " + request);
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        System.err.println("User not found or server error.");
                        return null;
                    }
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(response.body(), UserModel.class);
                    } catch (JsonProcessingException e) {
                        System.err.println("Failed to parse response: " + e.getMessage());
                        return null;
                    }
                })
                .exceptionally(throwable -> {
                    System.err.println("Failed to fetch user info: " + throwable.getMessage());
                    return null;
                });
    }

    
    public static CompletableFuture<UserModel> sentUserForRegistration(String url, String username, String email, String login, String password) {
        UserModel user = new UserModel(username, email, login, password);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to serialize user data: " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(), UserModel.class);
                        } catch (JsonProcessingException e) {
                            System.err.println("Failed to parse response: " + e.getMessage());
                            return null;
                        }
                    } else {
                        System.err.println("Registration failed with status code: " + response.statusCode());
                        return null;
                    }
                })
                .exceptionally(throwable -> {
                    System.err.println("Registration request failed: " + throwable.getMessage());
                    return null;
                });
    }


}