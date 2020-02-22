package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class CurrencyService {

    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();

    public double getCurrency(String code) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "currency/" + code)
                .encode()
                .build()
                .toUri();
        return restTemplate.getForObject(url, double.class);
    }
}
