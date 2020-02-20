package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.config.JsonBuilder;
import com.transfer.app7b.domain.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();
    private JsonBuilder<UserDto> jsonBuilder = new JsonBuilder<>();
    private List<UserDto> userDtos;

    private static UserService userService;

    private UserService() {
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public Set<UserDto> getUserDtos() {
        return new HashSet<>(userDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "user")
                .encode()
                .build()
                .toUri();
        Optional<UserDto[]> users = Optional.ofNullable(restTemplate.getForObject(url, UserDto[].class));
        userDtos = new ArrayList<>(users
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }

    public List<UserDto> fillterByEmail(String string) {
        string = string.toUpperCase();
        String finalString = string;
        return userDtos.stream()
                .filter(userDto -> userDto.getEmail().contains(finalString))
                .collect(Collectors.toList());
    }

    public List<UserDto> fillterByPesel(String string) {
        string = string.toUpperCase();
        String finalString = string;
        return userDtos.stream()
                .filter(userDto -> userDto.getPesel().contains(finalString))
                .collect(Collectors.toList());
    }

    public void save(UserDto userDto) {
        String url = appConfig.getBackendEndpoint() + "user";
        try {
            restTemplate.postForObject(url, (userDto), Void.class);
        } catch (RestClientException e) {
            LOGGER.error("" + e);
        }
    }

    public void update(UserDto userDto) {
        String url = appConfig.getBackendEndpoint() + "user";
        restTemplate.put(url, jsonBuilder.prepareJson(userDto));
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "user/" + id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }
}
