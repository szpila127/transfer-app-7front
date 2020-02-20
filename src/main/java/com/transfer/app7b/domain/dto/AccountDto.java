package com.transfer.app7b.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {
    @JsonProperty
    private long id;
    @JsonProperty
    private BigDecimal balance;
    @JsonProperty
    private String currency;
    @JsonProperty
    private String userId;

    public AccountDto(BigDecimal balance, String currency, String userId) {
        this.balance = balance;
        this.currency = currency;
        this.userId = userId;
    }
}
