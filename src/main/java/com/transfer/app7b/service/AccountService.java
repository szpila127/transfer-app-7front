package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.domain.dto.AccountDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@NoArgsConstructor
public class AccountService {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private AppConfig appConfig = AppConfig.getInstance();

    private List<AccountDto> accountDtos;
    private static AccountService accountService;

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
            URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "/account")
                    .encode()
                    .build()
                    .toUri();
            AccountDto[] accounts = restTemplate.getForObject(url, AccountDto[].class);
            accountDtos = Arrays.asList(Optional.ofNullable(accounts).orElse(new AccountDto[0]));
    }
}
