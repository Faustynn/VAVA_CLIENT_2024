package org.main.unimap_pc.client.configs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class AppConfig {
    // General app settings
    private static final String APP_TITLE = "UniMap";
    private static final String ICON_PATH = "/org/main/unimap_pc/images/GPS_app.png";
    private static final String GIT_HUB = "https://github.com/Faustynn/VAVA_2024";

    // FXMLs
    private static final String LOGIN_PAGE_PATH = "/org/main/unimap_pc/views/LoginPage.fxml";
    private static final String MAIN_PAGE_PATH = "/org/main/unimap_pc/views/MainPage.fxml";
    private static final String SIGNUP_PAGE_PATH = "/org/main/unimap_pc/views/SignUpPage.fxml";
    private static final String FORGOT_PASS_PAGE_PATH = "/org/main/unimap_pc/views/ForgotPass.fxml";
    private static final String FORGOT_PASS_PAGE_PATH2 = "/org/main/unimap_pc/views/ForgotPass_second.fxml";
    private static final String ERR_PAGE_PATH = "/org/main/unimap_pc/views/ErrorPage.fxml";

    // APIs
    private static final String API_URL = "http://localhost:8080/api/unimap_pc/";
    private static final String CHECK_CONNECTION_URL = API_URL + "check-connection";
    private static final String AUTH_URL = API_URL + "authenticate";
    private static final String REGISTR_URL = API_URL + "register";
    private static final String FIND_USER_BY_EMAIL_URL = API_URL + "user/email/";
    private static final String CONFIRM_CODE_TO_EMAIL = API_URL + "user/email/code";
    private static final String CHANGE_PASSWORD = API_URL + "user/email/password";
    private static final String CREATE_USER = API_URL + "user/create";

    // Getters
    public static String getLoginPagePath() {
        return LOGIN_PAGE_PATH;
    }
    public static String getMainPagePath() {
        return MAIN_PAGE_PATH;
    }
    public static String getSignupPagePath() {
        return SIGNUP_PAGE_PATH;
    }
    public static String getForgotPassPagePath() {
        return FORGOT_PASS_PAGE_PATH;
    }
    public static String getErrPagePath() {
        return ERR_PAGE_PATH;
    }
    public static String getCheckConnectionUrl() {
        return CHECK_CONNECTION_URL;
    }
    public static String getAuthUrl() {
        return AUTH_URL;
    }
    public static String getFindUserByEmailUrl() {
        return FIND_USER_BY_EMAIL_URL;
    }
    public static String getApiUrl() {
        return API_URL;
    }
    public static String getAppTitle() {
        return APP_TITLE;
    }
    public static String getIconPath() {
        return ICON_PATH;
    }
    public static String getCreateUser() {
        return CREATE_USER;
    }
    public static String getGithubPage() {
        return GIT_HUB;
    }
    public static String getRegistrUrl() {
        return REGISTR_URL;
    }
    public static String getForgotPassPagePath2() {
        return FORGOT_PASS_PAGE_PATH2;
    }
    public static String getConfirmCodeToEmail() {
        return CONFIRM_CODE_TO_EMAIL;
    }
    public static String getChangePassword() {
        return CHANGE_PASSWORD;
    }

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getResourceAsStream("/org/main/unimap_pc/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file is null");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getAesAlgorithm() {
        return properties.getProperty("AES_ALGORITHM");
    }

    public static String getSecretKey() {
        return properties.getProperty("SECRET_KEY");
    }

    public static String getIv() {
        return properties.getProperty("IV");
    }
}