package org.main.unimap_pc.client.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.models.NewsModel;


public class NewsController
{
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public static List<NewsModel> getAllNews() throws IOException, InterruptedException {
        URI uri = URI.create(AppConfig.getNewsUrl()); // Using AppConfig.getNewsUrl()
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<List<NewsModel>>() {
            });
        } else {
            System.err.println("Failed to fetch news: " + response.statusCode());
            return null;
        }
    }
}
