package com.transfer.app7b.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreCOnfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}