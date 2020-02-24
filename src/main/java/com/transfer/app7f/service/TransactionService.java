package com.transfer.app7f.service;

import com.transfer.app7f.config.AppConfig;
import com.transfer.app7f.config.JsonBuilder;
import com.transfer.app7f.domain.dto.TransactionDto;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private RestTemplate restTemplate = new RestTemplate();
    private JsonBuilder<TransactionDto> jsonBuilder = new JsonBuilder<>();
    private List<TransactionDto> transactionDtos;

    public Set<TransactionDto> getTransactionDtos() {
        return new HashSet<>(transactionDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "transaction")
                .encode()
                .build()
                .toUri();
        Optional<TransactionDto[]> transactions = Optional.ofNullable(restTemplate.getForObject(url, TransactionDto[].class));
        transactionDtos = new ArrayList<>(transactions
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
                .filter(transactionDto -> transactionDto.getDate().toString().contains(string))
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
        String url = AppConfig.backendEndpoint + "transaction";
        try {
            restTemplate.postForObject(url, (transactionDto), Void.class);
        } catch (RestClientException e) {
            LOGGER.error("" + e);
        }
    }

    public void returnTransaction(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "transaction/" + id)
                .encode()
                .build()
                .toUri();
        try {
            restTemplate.postForObject(url, Object.class, Void.class);
        } catch (RestClientException e) {
            LOGGER.error("" + e);
        }
    }

    public void update(TransactionDto transactionDto) {
        String url = AppConfig.backendEndpoint + "transaction";
        restTemplate.put(url, jsonBuilder.prepareJson(transactionDto));
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "transaction/" + id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }
}
