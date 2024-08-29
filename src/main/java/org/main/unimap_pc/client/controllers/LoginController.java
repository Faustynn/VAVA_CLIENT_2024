package org.main.unimap_pc.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Простая проверка логина и пароля
        if (username.equals("user") && password.equals("password")) {
            errorMessage.setText("Login successful!");
            // Открыть новое окно или перейти на другую сцену
        } else {
            errorMessage.setText("Invalid username or password");
        }
    }
}
