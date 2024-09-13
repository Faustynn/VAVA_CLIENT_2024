package org.main.unimap_pc.client.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorScreens {

    public static void showErrorScreen(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }



}

