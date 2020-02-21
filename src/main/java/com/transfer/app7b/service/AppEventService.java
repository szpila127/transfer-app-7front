package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.config.JsonBuilder;
import com.transfer.app7b.domain.dto.AppEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class AppEventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppEventService.class);

    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();
    private JsonBuilder<AppEventDto> jsonBuilder = new JsonBuilder<>();
    private List<AppEventDto> appEventDtos;

    private static AppEventService appEventService;

    private AppEventService() {
    }

    public static AppEventService getInstance() {
        if (appEventService == null) {
            appEventService = new AppEventService();
        }
        return appEventService;
    }

    public Set<AppEventDto> getAppEventDtos() {
        return new HashSet<>(appEventDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "event")
                .encode()
                .build()
                .toUri();
        Optional<AppEventDto[]> events = Optional.ofNullable(restTemplate.getForObject(url, AppEventDto[].class));
        appEventDtos = new ArrayList<>(events
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }

    public List<AppEventDto> filterByEvent(String string) {
        string = string.toUpperCase();
        String finalString = string;
        return appEventDtos.stream()
                .filter(appEventDto -> appEventDto.getEvent().contains(finalString))
                .collect(Collectors.toList());
    }

    public List<AppEventDto> filterByDate(String string) {
        return appEventDtos.stream()
                .filter(appEventDto -> appEventDto.getDate().contains(string))
                .collect(Collectors.toList());
    }
}
