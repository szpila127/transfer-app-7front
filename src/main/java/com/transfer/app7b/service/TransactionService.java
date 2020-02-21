package com.transfer.app7b.service;

import com.transfer.app7b.config.AppConfig;
import com.transfer.app7b.config.JsonBuilder;
import com.transfer.app7b.domain.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private RestTemplate restTemplate = new RestTemplate();
    private AppConfig appConfig = AppConfig.getInstance();
    private JsonBuilder<TransactionDto> jsonBuilder = new JsonBuilder<>();
    private List<TransactionDto> transactionDtos;

    private static TransactionService transactionService;

    private TransactionService() {
    }

    public static TransactionService getInstance() {
        if (transactionService == null) {
            transactionService = new TransactionService();
        }
        return transactionService;
    }

    public Set<TransactionDto> getTransactionDtos() {
        return new HashSet<>(transactionDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "transaction")
                .encode()
                .build()
                .toUri();
        Optional<TransactionDto[]> users = Optional.ofNullable(restTemplate.getForObject(url, TransactionDto[].class));
        transactionDtos = new ArrayList<>(users
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }

    public List<TransactionDto> filterByCurrency(String string) {
        string = string.toUpperCase();
        String finalString = string;
        return transactionDtos.stream()
                .filter(transactionDto -> transactionDto.getCurrency().contains(finalString))
                .collect(Collectors.toList());
    }

    public List<TransactionDto> filterByDate(String string) {
        return transactionDtos.stream()
                .filter(transactionDto -> transactionDto.getDate().contains(string))
                .collect(Collectors.toList());
    }

    public List<TransactionDto> filterByAccountOutId(String id) {
        return transactionDtos.stream()
                .filter(transactionDto -> transactionDto.getAccountOutId().contains(id))
                .collect(Collectors.toList());
    }

    public List<TransactionDto> filterByAccountInId(String id) {
        return transactionDtos.stream()
                .filter(transactionDto -> transactionDto.getAccountInId().contains(id))
                .collect(Collectors.toList());
    }

    public void save(TransactionDto transactionDto) {
        String url = appConfig.getBackendEndpoint() + "transaction";
        try {
            restTemplate.postForObject(url, (transactionDto), Void.class);
        } catch (RestClientException e) {
            LOGGER.error("" + e);
        }
    }

    public void update(TransactionDto transactionDto) {
        String url = appConfig.getBackendEndpoint() + "transaction";
        restTemplate.put(url, jsonBuilder.prepareJson(transactionDto));
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(appConfig.getBackendEndpoint() + "user/" + id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }
}
