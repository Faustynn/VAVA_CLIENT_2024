package org.main.unimap_pc.client.controllers;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.services.AuthService;
import org.main.unimap_pc.client.utils.LoadingScreens;

public class LogInController {
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

    @FXML
    private AnchorPane mainContentArea;

    @Getter
    private static SceneController sceneController;

    @FXML
    private Label infoMess;

    // If click into downlApp label open github page
    @FXML
    private void handleDownlApp() {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(AppConfig.getGithubPage()));
        } catch (IOException e) {
            showErrorDialog("Error opening the GitHub page: " + e.getMessage());
        }
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }

    // Modal Window logic
    @FXML
    private void openModalWindow(String fxmlPath, String windowTitle, String errorMessage) {
        try {
            Stage parentStage = (Stage) ((Node) (windowTitle.equals("Forgot Password") ? btnForgotPass : btnSignup)).getScene().getWindow();
            AnchorPane modalPane = LoadingScreens.loadFXML(fxmlPath);

            Scene modalScene = new Scene(modalPane);
            Stage modalStage = new Stage();

            // Configure modal stage
            modalStage.initStyle(StageStyle.TRANSPARENT);
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(parentStage);
            modalStage.setTitle(windowTitle);

            modalStage.setScene(modalScene);

            // Create overlay for parent stage
            StackPane overlay = createOverlay(parentStage);

            // Add overlay to parent scene
            Scene parentScene = parentStage.getScene();
            AnchorPane parentRoot = (AnchorPane) parentScene.getRoot();
            parentRoot.getChildren().add(overlay);

            // Remove overlay when modal stage is closed
            modalStage.setOnHidden(event -> parentRoot.getChildren().remove(overlay));

            // Show and wait
            modalStage.showAndWait();

        } catch (IOException e) {
            showErrorDialog(errorMessage + ": " + e.getMessage());
        }
    }
    // Helper method to create overlay
    private StackPane createOverlay(Stage parentStage) {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setPrefSize(parentStage.getWidth(), parentStage.getHeight());

        // Block main screen and play system sound on click
        overlay.setOnMouseClicked(event -> {
            java.awt.Toolkit.getDefaultToolkit().beep();
        });

        return overlay;
    }

    // Updated method calls
    @FXML
    private void handleForgotPass() {
        openModalWindow(
                AppConfig.getForgotPassPagePath(),
                "Forgot Password",
                "Error loading the forgot password window"
        );
    }

    @FXML
    private void handleSignUp() {
        openModalWindow(
                AppConfig.getSignupPagePath(),
                "Sign Up",
                "Error loading the sign up window"
        );
    }

    @FXML
    private void handleSignIn() {
        String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            infoMess.setText("Please enter your username and password!");
            return;
        }
        if (username.length() < 3 || password.length() < 3) {
            infoMess.setText("Username and password must be at least 3 characters long!");
            return;
        }

        AuthService.login(username, password).thenAccept(isLoginSuccessful -> {
            Platform.runLater(() -> {
                if (isLoginSuccessful) {
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
        }).exceptionally(ex -> {
            Platform.runLater(() -> infoMess.setText("Login request failed. Please try again later."));
            return null;
        });
    }
}