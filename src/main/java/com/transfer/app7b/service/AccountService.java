package com.transfer.app7b.service;

import com.google.gson.Gson;
import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.domain.dto.AccountDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class AccountService {

    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();

    private List<AccountDto> accountDtos;
    private static AccountService accountService;

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (accountService == null) {
            accountService = new AccountService();
        }
        return accountService;
    }

    public Set<AccountDto> getAccountDtos() {
        return new HashSet<>(accountDtos);
    }

    public void fetchAll() {
            URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "account")
                    .encode()
                    .build()
                    .toUri();
            AccountDto[] accounts = restTemplate.getForObject(url, AccountDto[].class);
            accountDtos = Arrays.asList(Optional.ofNullable(accounts).orElse(new AccountDto[0]));
    }

    public List<AccountDto> filterByCurrency(String string) {
        string = string.toUpperCase();
        String finalString = string;
        return accountDtos.stream()
                .filter(accountDto -> accountDto.getCurrency().contains(finalString))
                .collect(Collectors.toList());
    }

    public List<AccountDto> filterByUserId(String id) {
        return accountDtos.stream()
                .filter(accountDto -> accountDto.getUserId().contains(id))
                .collect(Collectors.toList());
    }

    public void save(AccountDto accountDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "account")
                .encode()
                .build()
                .toUri();
        restTemplate.postForObject(url, accountDto, Void.class);
    }

    public void update(AccountDto accountDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "account")
                .encode()
                .build()
                .toUri();
        Gson gson = new Gson();
        String jsonContent = gson.toJson(accountDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent,headers);
        restTemplate.put(url, httpEntity);
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "account/" + id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }
}
