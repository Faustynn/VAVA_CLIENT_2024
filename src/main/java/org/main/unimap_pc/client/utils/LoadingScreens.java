package org.main.unimap_pc.client.utils;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LoadingScreens {

    public static void showLoadScreen(Stage stage) {
        ProgressIndicator loadingIndicator = new ProgressIndicator();

        Image appImage = new Image(LoadingScreens.class.getResource("/org/main/unimap_pc/images/GPS_app.png").toExternalForm());
        ImageView imageView = new ImageView(appImage);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);

        // text label with animtion
        Label loadingText = new Label("Loading, please wait");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> loadingText.setText("Loading, please wait")),
                new KeyFrame(Duration.seconds(0.5), event -> loadingText.setText("Loading, please wait.")),
                new KeyFrame(Duration.seconds(1), event -> loadingText.setText("Loading, please wait..")),
                new KeyFrame(Duration.seconds(1.5), event -> loadingText.setText("Loading, please wait..."))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        StackPane loadingPane = new StackPane();
        loadingPane.getChildren().addAll(imageView, loadingIndicator, loadingText);
        StackPane.setMargin(imageView, new Insets(-350, 0, 0, 0));
        StackPane.setMargin(loadingIndicator, new Insets(-50, 0, 0, 0));
        StackPane.setMargin(loadingText, new Insets(100, 0, 0, 0));

        Scene loadingScene = new Scene(loadingPane, 1000, 600);
        stage.setScene(loadingScene);
        stage.show();
    }
}