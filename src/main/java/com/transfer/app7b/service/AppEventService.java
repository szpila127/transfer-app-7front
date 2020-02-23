package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.domain.dto.AppEventDto;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class AppEventService {

    private RestTemplate restTemplate = new RestTemplate();
    private List<AppEventDto> appEventDtos;

    public Set<AppEventDto> getAppEventDtos() {
        return new HashSet<>(appEventDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "event")
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
