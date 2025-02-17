package org.main.unimap_pc.client.services;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.main.unimap_pc.client.configs.AppConfig;


public class AuthService {
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    public static final Preferences prefs = Preferences.userNodeForPackage(AuthService.class);
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static CompletableFuture<Boolean> login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
               // System.out.println("non encrypted data: " + username + ":" + password);
                String data = username + ":" + password;
               // System.out.println("Encrypted data: " + encryptedData);
                return sendAuthenticationRequest(AppConfig.getAuthUrl(), data).join();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    private static CompletableFuture<Boolean> sendAuthenticationRequest(String url, String encryptedData) {
        if (encryptedData == null) {
            return CompletableFuture.completedFuture(false);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("data", encryptedData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        System.out.println("Full Request: " + request.toString());
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode jsonNode = objectMapper.readTree(response.body());
                            JsonNode userNode = jsonNode.get("user");
                            String accessToken = jsonNode.get("accessToken").asText();

                            String refreshToken = response.headers().firstValue("Set-Cookie")
                                    .map(cookie -> {
                                        for (String part : cookie.split(";")) {
                                            if (part.trim().startsWith("refreshToken=")) {
                                                return part.substring("refreshToken=".length());
                                            }
                                        }
                                        return null;
                                    }).orElse(null);

                            if (accessToken != null && refreshToken != null) {
                                prefs.put("ACCESS_TOKEN", accessToken);
                                prefs.put("REFRESH_TOKEN", refreshToken);
                                prefs.put("USER_DATA", userNode.toString());
                                scheduler.schedule(() -> prefs.remove("USER_DATA"), 30, TimeUnit.MINUTES);
                              //  System.out.println("Access Token: " + accessToken + "\nRefresh Token: " + refreshToken);
                                return true;
                            } else {
                                System.err.println("Tokens not found in the response.");
                                return false;
                            }
                        } catch (Exception e) {
                            System.err.println("Failed to parse JSON response: " + e.getMessage());
                            return false;
                        }
                    } else {
                        System.err.println("Authentication failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    System.err.println("Authentication request failed: " + throwable.getMessage());
                    return false;
                });

    }
}