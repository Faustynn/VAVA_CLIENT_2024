package org.main.unimap_pc.client.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;
import org.main.unimap_pc.client.configs.AppConfig;
import org.main.unimap_pc.client.services.EmailService;
import org.main.unimap_pc.client.utils.Encryptor;

import java.io.IOException;
import static org.main.unimap_pc.client.configs.AppConfig.getForgotPassPagePath2;
import static org.main.unimap_pc.client.controllers.LogInController.showErrorDialog;

public class ForgotPassController {
    @FXML
    private Label closeApp;

    @FXML
    private AnchorPane dragArea;

    @FXML
    private MFXButton btnSendMail;

    @FXML
    private MFXTextField fieldEmail;

    @FXML
    private MFXTextField fieldCode;

    @FXML
    private Label infoMess;

    @FXML
    private Label infoMess2;

    @Setter
    private String email;


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
    private void SendMailbtn(MouseEvent mouseEvent) {
        if (fieldEmail.getText().isEmpty()) {
            infoMess.setText("Please enter your email address");
        } else if (fieldEmail.getText().length() < 5 || !fieldEmail.getText().contains("@") || !fieldEmail.getText().contains(".")) {
            infoMess.setText("Invalid email address");
            // fieldEmail.clear();
        } else {
            String url = AppConfig.getFindUserByEmailUrl() + fieldEmail.getText();
            System.out.println(url);
            EmailService.checkEmail(url, fieldEmail.getText()).thenAccept(result -> {
                Platform.runLater(() -> {
                    if (!result) {
                        infoMess.setText("User with this email dont exist!");
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(getForgotPassPagePath2()));
                            Parent root = loader.load();
                            ForgotPassController controller = loader.getController();
                            controller.setEmail(fieldEmail.getText());
                            Stage stage = (Stage) fieldEmail.getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (IOException e) {
                            infoMess.setText("Failed to load next page, please try again!");
                        }
                    }
                });
            });
        }
    }


    @FXML
    public void ConfirmMailbtn(MouseEvent mouseEvent) throws Exception {
        String url = AppConfig.getConfirmCodeToEmail();
        String url2 = AppConfig.getChangePassword();

        if (fieldCode.getText().isEmpty()) {
            infoMess2.setText("Please enter your code from email!");
        } else if (fieldCode.getText().length() != 6) {
            infoMess2.setText("The code must be 6 numbers!");
        } else {
            EmailService.checkCode(url, fieldCode.getText(), email).thenAccept(result -> {
                Platform.runLater(() -> {
                    if (!result) {
                        infoMess2.setText("Invalid code!");
                    } else {
                        infoMess2.setText("Please enter your new password!");
                        fieldCode.clear();

                        fieldCode.textProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue.length() < 6) {
                                infoMess2.setText("Password must be at least 6 characters!");
                                return;
                            }

                            try {
                                String new_password = Encryptor.encrypt(newValue);
                                fieldCode.setDisable(true);

                                EmailService.updatepassword(url2, new_password, email).thenAccept(result2 -> {
                                    Platform.runLater(() -> {
                                        if (!result2) {
                                            infoMess2.setText("Failed to change password. Please try again.");
                                            fieldCode.setDisable(false);
                                        } else {
                                            infoMess2.setText("Password changed successfully!");
                                            Stage stage = (Stage) fieldCode.getScene().getWindow();
                                            stage.close();

                                        }
                                    });
                                });
                            } catch (Exception e) {
                                showErrorDialog("Encryption error: " + e.getMessage());
                            }
                        });
                    }
                });
            });
        }
    }
}