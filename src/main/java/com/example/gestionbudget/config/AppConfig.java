package com.example.gestionbudget.config;

public class AppConfig {
    public static String getBasePathForOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        String basePath;

        if (osName.contains("win")) {
            basePath = System.getenv("APPDATA");
        } else if (osName.contains("mac")) {
            basePath = System.getProperty("user.home") + "/Library/Application Support";
        } else if (osName.contains("nux") || osName.contains("nix") || osName.contains("aix")) {
            basePath = System.getProperty("user.home");
        } else {
            basePath = System.getProperty("user.home");
        }

        return basePath;
    }
}
