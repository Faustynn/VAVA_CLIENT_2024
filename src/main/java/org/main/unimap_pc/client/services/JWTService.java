package org.main.unimap_pc.client.services;

import lombok.Setter;
import org.main.unimap_pc.client.utils.TokenRefresher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.main.unimap_pc.client.configs.AppConfig;

import java.io.IOException;
import java.util.Objects;

public class JWTService {

    @Setter
    private TokenRefresher tokenRefresher;

    public void refreshTokenService() {
        if (validateRefreshToken()) {
            AuthService.refreshAccessToken().thenAccept(isTokenRefreshed -> {
                if (isTokenRefreshed) {
                    if (tokenRefresher != null) {
                        tokenRefresher.startTokenRefreshTask();
                    }
                } else {
                    handleInvalidToken();
                }
            });
        } else {
            handleInvalidToken();
        }
    }

    public boolean validateRefreshToken() {
        AuthService.validateRefreshToken().thenAccept(isTokenValid -> {
            if (!isTokenValid) {
                AuthService.prefs.remove("REFRESH_TOKEN");
            }
        });

        String refreshToken = AuthService.prefs.get("REFRESH_TOKEN", null);

        return refreshToken != null && !refreshToken.isEmpty();
    }

    private void handleInvalidToken() {
        if (tokenRefresher != null) {
            tokenRefresher.stopTokenRefreshTask();
        }
        redirectToLoginPage();
    }

    private void redirectToLoginPage() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AppConfig.getLoginPagePath())));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}