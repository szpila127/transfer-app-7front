package com.transfer.app7f.service;

import com.transfer.app7f.config.AppConfig;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
public class CurrencyService {

    private RestTemplate restTemplate = new RestTemplate();

    public static double EUR;
    public static double USD;
    public static double GBP;

    public void getCurrencies() {
        String urlEur = AppConfig.backendEndpoint + "currency/eur";
        String urlUsd = AppConfig.backendEndpoint + "currency/usd";
        String urlGbp = AppConfig.backendEndpoint + "currency/gbp";
        EUR = (double) restTemplate.getForObject(urlEur, Object.class);
        USD = (double) restTemplate.getForObject(urlUsd, Object.class);
        GBP = (double) restTemplate.getForObject(urlGbp, Object.class);
    }
}
