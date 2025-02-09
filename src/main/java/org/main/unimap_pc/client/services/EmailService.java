package org.main.unimap_pc.client.services;


import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class EmailService {
    private static final HttpClient httpClient = HttpClient.newBuilder().build();


    public static CompletableFuture<Boolean> checkEmail(String url, String email) {
        if (email == null) {
            return CompletableFuture.completedFuture(false);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("data", email);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return true;
                    } else {
                        System.err.println("Check Email failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    System.err.println("Check Email request failed: " + throwable.getMessage());
                    return false;
                });
    }
    public static CompletableFuture<Boolean> checkCode(String url,String code, String email) {
        if (code == null || code.isEmpty() || email == null || email.isEmpty()) {
            return CompletableFuture.completedFuture(false);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("data", email +":"+code);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return true;
                    } else {
                        System.err.println("Check Code failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    System.err.println("Check Code request failed: " + throwable.getMessage());
                    return false;
                });
    }
    public static CompletableFuture<Boolean> updatepassword(String url,String new_password, String email) {
        if (new_password == null || new_password.isEmpty() || email == null || email.isEmpty()) {
            return CompletableFuture.completedFuture(false);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("data", email+":"+new_password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return true;
                    } else {
                        System.err.println("Updating password failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    System.err.println("Update password request failed: " + throwable.getMessage());
                    return false;
                });
    }
}
