package org.main.unimap_pc.client.controllers;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.models.UserModel;
import org.main.unimap_pc.client.services.RegistrationService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;
import static org.main.unimap_pc.client.utils.ErrorScreens.showErrorScreen;

public class SignUpController {
    @FXML
    private Label closeApp;

    @FXML
    private AnchorPane dragArea;

    @FXML
    private void handleCloseApp() {
        Stage stage = (Stage) closeApp.getScene().getWindow();
        stage.close();
    }


    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void initialize() {
        dragArea.setOnMousePressed(this::handleMousePressed);
        dragArea.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) dragArea.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }


    @FXML
    private Label infoMess;
    @FXML
    private MFXTextField fieldUsername;
    @FXML
    private MFXTextField fieldEmail;
    @FXML
    private MFXTextField fieldLogin;
    @FXML
    private MFXPasswordField fieldPassword;
    @FXML
    private MFXPasswordField fieldConfirmPassword;


    @FXML
    private void handleRegisterBtn() throws IOException {
        if (fieldLogin.getText().isEmpty()) {
            infoMess.setText("Please enter your login!");
            return;
        }
        if (fieldEmail.getText().isEmpty()) {
            infoMess.setText("Please enter your email!");
            return;
        }
        if (fieldEmail.getText().length() < 5 || !fieldEmail.getText().contains("@")) {
            infoMess.setText("Please write correct email!");
            return;
        }
        if (fieldUsername.getText().isEmpty()) {
            infoMess.setText("Please enter your username!");
            return;
        }
        if (fieldPassword.getText().isEmpty()) {
            infoMess.setText("Please enter your password!");
            return;
        }
        if (fieldPassword.getText().length() < 8) {
            infoMess.setText("Write minimal 8 characters!");
            return;
        }
        if (fieldConfirmPassword.getText().isEmpty() || !fieldConfirmPassword.getText().equals(fieldPassword.getText())) {
            infoMess.setText("Please check correctness of your password!");
            return;
        }

        infoMess.setText("Registration in progress...");
        final int maxRetries = 3;
        final int[] currentRetry = {0};

        CompletableFuture<UserModel> registrationFuture = RegistrationService.sentUserForRegistration(
                AppConfig.getCreateUser(),
                fieldUsername.getText(),
                fieldEmail.getText(),
                fieldLogin.getText(),
                fieldPassword.getText()
        );

        CompletableFuture<UserModel> retryableRegistration = registrationFuture.thenCompose(result -> {
            if (result != null) {
                return CompletableFuture.completedFuture(result);
            } else if (currentRetry[0] < maxRetries) {
                currentRetry[0]++;
                System.out.println("Retry attempt " + currentRetry[0] + " of " + maxRetries);

                // Create a new CompletableFuture that will complete after the delay
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        return null;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }).thenCompose(v -> RegistrationService.sentUserForRegistration(
                        AppConfig.getCreateUser(),
                        fieldUsername.getText(),
                        fieldEmail.getText(),
                        fieldLogin.getText(),
                        fieldPassword.getText()
                ));
            } else {
                return CompletableFuture.completedFuture(null);
            }
        });

        retryableRegistration.thenAccept(userModel -> {
            Platform.runLater(() -> {
                if (userModel != null) {
                    // Replace scene into new scene
                    Stage currentStage = (Stage) dragArea.getScene().getWindow();
                    SceneController sceneController = new SceneController(currentStage);

                    try {
                        sceneController.createAndShowNewScene(AppConfig.getForgotPassPagePath());
                    } catch (IOException e) {
                        showErrorScreen("Error loading page, please try again later!");
                    }
                } else {
                    showErrorScreen("Registration failed after " + maxRetries + " attempts. Please try again later.");
                }
            });
        }).exceptionally(throwable -> {
            Platform.runLater(() -> showErrorScreen("Error during registration: " + throwable.getMessage()));
            return null;
        });
    }

}