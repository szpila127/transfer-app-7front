package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.config.JsonBuilder;
import com.transfer.app7b.domain.dto.UserDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class UserService {

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
}
