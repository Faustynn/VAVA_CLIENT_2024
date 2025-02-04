package org.main.unimap_pc.client.configs;


public class AppConfig {
    // General app setings
    private static final String APP_TITLE = "UniMap";
    private static final String ICON_PATH = "/org/main/unimap_pc/images/GPS_app.png";
    private static final String GIT_HUB = "https://github.com/Faustynn/VAVA_2024";

    // Example keys
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY = "0123456789abcdef0123456789abcdef"; // 32-byte key
    private static final String IV = "0123456789abcdef"; // 16-byte IV

    // FXLMs
    private static final String LOGIN_PAGE_PATH = "/org/main/unimap_pc/views/LoginPage.fxml";
    private static final String MAIN_PAGE_PATH = "/org/main/unimap_pc/views/MainPage.fxml";
    private static final String SIGNUP_PAGE_PATH = "/org/main/unimap_pc/views/SignUpPage.fxml";
    private static final String FORGOT_PASS_PAGE_PATH = "/org/main/unimap_pc/views/ForgotPass.fxml";
    private static final String ERR_PAGE_PATH = "/org/main/unimap_pc/views/ErrorPage.fxml";

    // APIs
    private static final String API_URL = "http://localhost:8080/api/unimap_pc/";
    private static final String CHECK_CONNECTION_URL = API_URL + "check-connection";
    private static final String AUTH_URL = API_URL + "authenticate";
    private static final String REGISTR_URL = API_URL + "register";

    private static final String GET_USER_BY_EMAIL_URL = API_URL + "user/email/";
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
    public static String getGetUserByEmailUrl() {
        return GET_USER_BY_EMAIL_URL;
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
        return GIT_HUB; }

    public static String getAesAlgorithm() {
        return AES_ALGORITHM;
    }
    public static String getSecretKey() {
        return SECRET_KEY;
    }
    public static String getIv() {
        return IV;
    }
    public static String getRegistrUrl() {
        return REGISTR_URL;
    }
}
