package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.domain.dto.AppEventDto;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class AppEventService {


    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();

    private List<AppEventDto> appEventDtos;
    private static AppEventService appEventService;

    public static AppEventService getInstance() {
        if (appEventService == null) {
            appEventService = new AppEventService();
        }
        return appEventService;
    }

    public Set<AppEventDto> getAppEventStos() {
        return new HashSet<>(appEventDtos);
    }
}
