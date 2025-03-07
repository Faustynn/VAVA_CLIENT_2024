package org.main.unimap_pc.client.services;


import org.json.JSONObject;
import org.main.unimap_pc.client.utils.Logger;

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
                        Logger.error("Check Email failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    Logger.error("Check Email request failed: " + throwable.getMessage());
                    return false;
                });
    }

    public static CompletableFuture<Boolean> checkCode(String url,String code, String email) {
        if (code == null || code.isEmpty() || email == null || email.isEmpty()) {
            System.out.println("EROROR CHECK CODEEE");
            return CompletableFuture.completedFuture(false);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("data", email +":"+code);

        System.out.println("CHECK CODE "+ requestBody.toString());

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
                        Logger.error("Check Code failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    Logger.error("Check Code request failed: " + throwable.getMessage());
                    return false;
                });
    }
//
//    public static CompletableFuture<Boolean> changeEmailRequest(String url, String login, String email) { // Add login parameter
//        if (email == null || login == null) {
//            return CompletableFuture.completedFuture(false);
//        }
//
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("login", login); // Add login to the request body
//        requestBody.put("email", email);   // Add email to the request body
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .header("Authorization", "Bearer " + PreferenceServise.get("ACCESS_TOKEN"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
//                .build();
//
//        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(response -> {
//                    if (response.statusCode() == 200) {
//                        // Parse the response body if needed
//                        return true; // Or parse the response as needed
//                    } else {
//                        System.err.println("Check Email failed with status code: " + response.statusCode());
//                        return false;
//                    }
//                })
//                .exceptionally(throwable -> {
//                    System.err.println("Check Email request failed: " + throwable.getMessage());
//                    return false;
//                });
//    }
//
//    public static CompletableFuture<Boolean> verifyLoginCode(String url, String code, String login) { // Renamed function
//        if (code == null || code.isEmpty() || login == null || login.isEmpty()) {
//            System.err.println("Error: Login or code is missing for verification."); // Changed to System.err
//            return CompletableFuture.completedFuture(false);
//        }
//
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("data", login + ":" + code); // Changed to login:code
//
//        System.out.println("Verifying login code: " + requestBody.toString()); // More descriptive message
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .header("Authorization", "Bearer " + PreferenceServise.get("ACCESS_TOKEN"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
//                .build();
//
//        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(response -> {
//                    if (response.statusCode() == 200) {
//                        return true;
//                    } else {
//                        System.err.println("Login code verification failed with status code: " + response.statusCode()); // Changed message
//                        return false;
//                    }
//                })
//                .exceptionally(throwable -> {
//                    System.err.println("Login code verification request failed: " + throwable.getMessage()); // Changed message
//                    return false;
//                });
//    }
//

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
                        Logger.error("Updating password failed with status code: " + response.statusCode());
                        return false;
                    }
                })
                .exceptionally(throwable -> {
                    Logger.error("Update password request failed: " + throwable.getMessage());
                    return false;
                });
    }
}
