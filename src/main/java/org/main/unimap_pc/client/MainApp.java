package org.main.unimap_pc.client;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import org.main.unimap_pc.client.controllers.SceneController;
import org.main.unimap_pc.client.services.RestClientConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;

import static org.main.unimap_pc.client.utils.ErrorScreens.showErrorScreen;

public class MainApp extends Application {
    @Getter
    private static SceneController sceneController;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private static final String APP_TITLE = "UniMap";
    private static final String ICON_PATH = "/org/main/unimap_pc/images/GPS_app.png";
    private static final String CONNECTION_URL = "http://localhost:8080/api/unimap_pc/check-connection";


    @Override
    public void start(Stage stage) {
        stage.setTitle(APP_TITLE);
        setAppIcon(stage);

        checkServerConnectionAsync(stage);
    }


    private void setAppIcon(Stage stage) {
        try (InputStream iconStream = getClass().getResourceAsStream(ICON_PATH)) {
            if (iconStream == null) {
                throw new IllegalArgumentException("Icon file not found: " + ICON_PATH);
            }
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
        } catch (IOException e) {
         //   TO DO logging
        }
    }

    private void checkServerConnectionAsync(Stage stage) {
        CompletableFuture.supplyAsync(() -> RestClientConnection.checkConnection(CONNECTION_URL), executorService).thenAccept(isConnected -> {
            if (isConnected) {
                initializeSceneController(stage);
            } else {
                //   TO DO logging
                showErrorScreen("Server is not available. Please try again later.");
            }
        }).exceptionally(ex -> {
            //   TO DO logging
            showErrorScreen("Error connecting to the server. Please try again later.");
            return null;
        });
    }

    private void initializeSceneController(Stage stage) {
        try {
            sceneController = new SceneController(stage);
            sceneController.changeScene("/org/main/unimap_pc/views/LoginPage.fxml");
            stage.show();
        } catch (IOException e) {
            //   TO DO logging
            showErrorScreen("Error loading the application. Please try again later.");
        }
    }

    @Override
    public void stop() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                //   TO DO logging
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            //   TO DO logging
            executorService.shutdownNow();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
