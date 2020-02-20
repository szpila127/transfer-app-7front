package com.transfer.app7b.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppConfig {
    private static  AppConfig appConfig;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    private String backendEndpoint = "http://localhost:8080/v1/ta7/";
}
