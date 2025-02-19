package org.main.unimap_pc.client.controllers;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.main.unimap_pc.client.utils.LanguageManager;
import org.main.unimap_pc.client.utils.LanguageSupport;


public class MainPageController implements LanguageSupport {
    @FXML
    private Label closeApp;
    @FXML
    private void handleCloseApp() {
        Stage stage = (Stage) closeApp.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    private AnchorPane dragArea;
    @FXML
    private AnchorPane dragArea2;
    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
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

    private String accessToken;
    private String refreshToken;
    private String userData;
    private String cachedLanguage;

    @FXML
    private void initialize() {
        Preferences prefs = Preferences.userNodeForPackage(MainPageController.class);
        try {
            accessToken = prefs.get("ACCESS_TOKEN", null);
            refreshToken = prefs.get("REFRESH_TOKEN", null);
            userData = prefs.get("USER_DATA", null);
            cachedLanguage = prefs.get("LANGUAGE", "en");

            LanguageManager.changeLanguage(cachedLanguage);
            LanguageManager.getInstance().registerController(this);
         //   updateUILanguage(LanguageManager.getCurrentBundle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        dragArea.setOnMousePressed(this::handleMousePressed);
        dragArea.setOnMouseDragged(this::handleMouseDragged);

        Scene scene = dragArea.getScene();
        if (scene != null) {
            scene.widthProperty().addListener((obs, oldVal, newVal) -> {
                // TODO:Resize logic
            });
            scene.heightProperty().addListener((obs, oldVal, newVal) -> {
                // TODO:Resize logic
            });
        }
    }

    @FXML
    private MFXButton logoutbtn;
    @FXML
    private MFXToggleButton viewmode;
    @FXML
    private MFXButton btn_homepage;
    @FXML
    private MFXButton btn_profilepage;
    @FXML
    private MFXButton btn_subjectpage;
    @FXML
    private MFXButton btn_teacherspage;
    @FXML
    private MFXButton btn_settingspage;
    @FXML
    private Label buycoffe;
    @FXML
    private Label news_upd_text;
    @FXML
    private Label utils_text;
    @FXML
    private Label support_text;
    @FXML
    private Label descriptFIITDISCORD;
    @FXML
    private Label descriptFXcom;
    @FXML
    private Label descriptMladost;
    @FXML
    private Label descriptFIITTelegram;
    @FXML
    private MFXComboBox languageComboBox;

    @Override
    public void updateUILanguage(ResourceBundle languageBundle) {
        closeApp.setText(languageBundle.getString("close"));
        logoutbtn.setText(languageBundle.getString("logout"));

        btn_homepage.setText(languageBundle.getString("homepage"));
        btn_profilepage.setText(languageBundle.getString("profilepage"));
        btn_subjectpage.setText(languageBundle.getString("subjectpage"));
        btn_teacherspage.setText(languageBundle.getString("teacherspage"));
        btn_settingspage.setText(languageBundle.getString("settingspage"));
        languageComboBox.setPromptText(languageBundle.getString("language.combobox"));
        news_upd_text.setText(languageBundle.getString("news.updates"));
        utils_text.setText(languageBundle.getString("utils"));
        support_text.setText(languageBundle.getString("support"));
        descriptFIITDISCORD.setText(languageBundle.getString("descriptFIITDISCORD"));
        descriptFXcom.setText(languageBundle.getString("descriptFXcom"));
        descriptMladost.setText(languageBundle.getString("descriptMladost"));
        descriptFIITTelegram.setText(languageBundle.getString("descriptFIITTelegram"));
        buycoffe.setText(languageBundle.getString("buycoffe"));

        navi_login_text.setText(languageBundle.getString("login"));
        navi_username_text.setText(languageBundle.getString("username"));

        if (news_title != null) {
            news_title.setText(languageBundle.getString("news.title"));
        }
        if (news_descrip != null) {
            news_descrip.setText(languageBundle.getString("news.description"));
        }
    }


    @FXML
    private Label navi_login_text;
    @FXML
    private Label navi_username_text;
    @FXML
    private ImageView navi_avatar;
    @FXML
    private MFXButton navi_avatar_btn;
    @FXML
    private FontAwesomeIcon gitHubIcon;
    @FXML
    private FontAwesomeIcon TipsIcon;
    @FXML
    private MFXScrollPane scrollPane_news;
    @FXML
    private Pane pane_for_news;
    @FXML
    private AnchorPane news1;
    @FXML
    private Label news_title;
    @FXML
    private Label news_descrip;






}
