package org.main.unimap_pc.client.utils;

import lombok.Getter;
import org.main.unimap_pc.client.configs.AppConfig;

import java.util.prefs.Preferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static LanguageManager instance;
    @Getter
    private ResourceBundle currentBundle;
    private List<LanguageSupport> registeredControllers = new ArrayList<>();
    private Preferences prefs;

    private LanguageManager() {
        prefs = Preferences.userNodeForPackage(LanguageManager.class);
        loadCurrentLanguage();
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public void registerController(LanguageSupport controller) {
        try {
            registeredControllers.add(controller);
            controller.updateUILanguage(currentBundle);
        } catch (Exception e) {
            System.err.println("Failed to register controller: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeLanguage(String languageCode) {
        try {
            currentBundle = ResourceBundle.getBundle(AppConfig.getRESOURCE_PATHS().get(languageCode), new Locale(languageCode));
            for (LanguageSupport controller : registeredControllers) {
                controller.updateUILanguage(currentBundle);
            }
        } catch (Exception e) {
            System.err.println("Failed to change language: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getCachedLanguage() {
        return prefs.get(AppConfig.getLANGUAGE_KEY(), AppConfig.getDEFAULT_LANGUAGE());
    }
    public void putCachedLanguage(String languageCode) {
        prefs.put(AppConfig.getLANGUAGE_KEY(), languageCode);
    }

    private void loadCurrentLanguage() {
        try {
            String languageCode = AppConfig.getLANGUAGE_CODES().get(prefs.get(AppConfig.getLANGUAGE_KEY(), AppConfig.getDEFAULT_LANGUAGE()));
            currentBundle = ResourceBundle.getBundle(AppConfig.getRESOURCE_PATHS().get(languageCode), new Locale(languageCode));
        } catch (Exception e) {
            System.err.println("Failed to load current language: " + e.getMessage());
            e.printStackTrace();
        }
    }
}