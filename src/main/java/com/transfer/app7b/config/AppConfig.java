package com.transfer.app7b.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AppConfig {
    private static  AppConfig appConfig;

    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    @Value("${backend.endpoint}")
    private String backendEndpoint;
}
