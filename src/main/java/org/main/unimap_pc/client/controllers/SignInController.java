package org.main.unimap_pc.client.controllers;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.services.GetUserInfo;
import org.main.unimap_pc.client.utils.LoadingScreens;

public class SignInController {
    @FXML
    private MFXTextField fieldUsername;

    @FXML
    private MFXPasswordField fieldPassword;

    @FXML
    private Label btnForgotPass;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnSignup;

    @FXML
    private Label downlApp;

    @FXML
    private MFXButton btnGoogle;

    @FXML
    private MFXButton btnFacebook;

    @Getter
    private static SceneController sceneController;

    @FXML
    private Label infoMess;

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }

    @FXML
    private void handleForgotPass() {
        Stage currentStage = (Stage) btnForgotPass.getScene().getWindow();
        SceneController sceneController = new SceneController(currentStage);

        try {
            sceneController.createAndShowNewScene(AppConfig.getForgotPassPagePath());
        } catch (IOException e) {
         // TO DO
            showErrorDialog("Error loading page, please try again later!");
        }
    }

    @FXML
    private void handleSignUp() throws IOException {
        if (btnForgotPass != null) {
            Stage currentStage = (Stage) btnForgotPass.getScene().getWindow();
            sceneController = new SceneController(currentStage);
            sceneController.createAndShowNewScene("/org/main/unimap_pc/views/SignUpPage.fxml");
            currentStage.show();
        } else {
            System.err.println("btnForgotPass is null");
        }
    }

    @FXML
    private void handleSignIn() throws IOException {
        if (fieldUsername.getText().isEmpty()) {
            infoMess.setText("Please enter your username!");
            return;
        }
        if (fieldPassword.getText().isEmpty()) {
            infoMess.setText("Please enter your password!");
            return;
        }

        GetUserInfo.getUserByEmailOrLogin(AppConfig.getGetUserUrl(), fieldUsername.getText())
                .thenAccept(user -> {
                    Platform.runLater(() -> {
                        if (user == null) {
                            infoMess.setText("This User doesn't exist!");
                            return;
                        }
                        if (user.getPassword().equals(fieldPassword.getText())) {
                            try {
                                Stage currentStage = (Stage) btnSignin.getScene().getWindow();

                                sceneController = new SceneController(currentStage);
                                LoadingScreens.showLoadScreen(currentStage);

                                sceneController.replaceSceneContent(AppConfig.getMainPagePath());

                            } catch (IOException e) {
                                System.err.println("Failed to load main page: " + e.getMessage());
                                showErrorDialog("Error loading the application. Please try again later.");
                            }
                        } else {
                            infoMess.setText("Invalid username or password!");
                        }
                    });
                });
    }



}