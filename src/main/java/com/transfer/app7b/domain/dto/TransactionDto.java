package com.transfer.app7b.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String date;
    @JsonProperty
    private BigDecimal amount;
    @JsonProperty
    private String currency;
    @JsonProperty
    private String accountOutId;
    @JsonProperty
    private String accountInId;

    public Long getId() {
        return id;
    }

    public String getDate() {
        String newDate = date.replace('T', ' ');
        return newDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountOutId() {
        return accountOutId;
    }

    public String getAccountInId() {
        return accountInId;
    }
}
