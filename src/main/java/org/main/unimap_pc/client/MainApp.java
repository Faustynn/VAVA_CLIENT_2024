package org.main.unimap_pc.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.controllers.SceneController;
import org.main.unimap_pc.client.services.CheckClientConnection;
import org.main.unimap_pc.client.utils.LoadingScreens;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;
import static org.main.unimap_pc.client.utils.ErrorScreens.showErrorScreen;

public class MainApp extends Application {
    @Getter
    private static SceneController sceneController;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean connectionEstablished = false;

    private void setAppIcon(Stage stage) {
        try (InputStream iconStream = getClass().getResourceAsStream(AppConfig.getIconPath())) {
            if (iconStream == null) {
                throw new IllegalArgumentException("Icon file not found: " + AppConfig.getIconPath());
            }
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
        } catch (IOException e) {
            System.err.println("Failed to load application icon: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(AppConfig.getAppTitle());
        setAppIcon(stage);

        sceneController = new SceneController(stage);
        LoadingScreens.showLoadScreen(stage);

        checkServerConnectionAsync(stage);
        schedulePeriodicServerChecks(stage);
    }

    private void checkServerConnectionAsync(Stage stage) {
        if(!connectionEstablished){
            System.out.println("Checking server connection...");
        }else {
            System.out.println("Periodical Server Checking...");
        }
        attemptConnection(stage, 3);
    }
    private void showLoadingScreen(Stage stage) {
        Platform.runLater(() -> {
            LoadingScreens.showLoadScreen(stage);
        });
    }

    private void attemptConnection(Stage stage, int attemptsLeft) {
        CheckClientConnection.checkConnectionAsync(AppConfig.getCheckConnectionUrl())
                .thenAccept(isConnected -> {
                    if (isConnected) {
                        if (!connectionEstablished) {
                            connectionEstablished = true;
                            System.out.println("Connection successful!");
                            Platform.runLater(() -> {
                                try {
                                    sceneController.changeScene(AppConfig.getLoginPagePath());
                                    stage.show();
                                } catch (IOException e) {
                                    System.err.println("Failed to load login page: " + e.getMessage());
                                    showErrorAndExit("Error loading the application. Please try again later.");
                                }
                            });
                        }
                    } else {
                        if (connectionEstablished) {
                            connectionEstablished = false;
                            System.out.println("Connection lost, showing loading screen...");
                            showLoadingScreen(stage);
                        }
                        if (attemptsLeft > 1) {
                            System.out.println("Retrying connection...");
                            scheduler.schedule(() -> attemptConnection(stage, attemptsLeft - 1), 3, TimeUnit.SECONDS);
                        } else {
                            Platform.runLater(() ->
                                    showErrorAndExit("Server is not available. Please try again later.")
                            );
                        }
                    }
                })
                .exceptionally(ex -> {
                    if (connectionEstablished) {
                        connectionEstablished = false;
                        System.out.println("Connection lost, showing loading screen...");
                        showLoadingScreen(stage);
                    }
                    if (attemptsLeft > 1) {
                        System.out.println("Retrying connection...");
                        scheduler.schedule(() -> attemptConnection(stage, attemptsLeft - 1), 3, TimeUnit.SECONDS);
                    } else {
                        Platform.runLater(() ->
                                showErrorAndExit("Error connecting to the server. Please try again later.")
                        );
                    }
                    return null;
                });
    }

    private void schedulePeriodicServerChecks(Stage stage) {
        scheduler.scheduleAtFixedRate(() -> {
            checkServerConnectionAsync(stage);
        }, 0, 5, TimeUnit.SECONDS);
    }


    private void showErrorAndExit(String message) {
        showErrorScreen(message);
        stop();
    }
    @Override
    public void stop() {
        executorService.shutdown();
        scheduler.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}