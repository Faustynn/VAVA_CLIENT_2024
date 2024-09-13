package org.main.unimap_pc.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SignUpController {
    @FXML
    private Label closeApp;



    @FXML
    private void handleCloseApp() {
        Stage stage = (Stage) closeApp.getScene().getWindow();
        stage.close();
    }

}
