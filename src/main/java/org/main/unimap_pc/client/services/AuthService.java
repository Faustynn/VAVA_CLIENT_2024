package org.main.unimap_pc.client.services;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;
import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.utils.Encryptor;


public class AuthService {
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public static CompletableFuture<Boolean> login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
               // System.out.println("non encrypted data: " + username + ":" + password);
                String encryptedData = Encryptor.encrypt(username + ":" + password);
               // System.out.println("Encrypted data: " + encryptedData);
                return sendAuthenticationRequest(AppConfig.getAuthUrl(), encryptedData).join();
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
                        return true;
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