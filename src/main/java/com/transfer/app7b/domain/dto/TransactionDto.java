package com.transfer.app7b.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    @JsonProperty
    private long id;
    @JsonProperty
    private LocalDateTime date;
    @JsonProperty
    private BigDecimal amount;
    @JsonProperty
    private String currency;
    @JsonProperty
    private long accountOutId;
    @JsonProperty
    private long accountInId;

    public TransactionDto(BigDecimal amount, String currency, long accountOutId, long accountInId) {
        this.amount = amount;
        this.currency = currency;
        this.accountOutId = accountOutId;
        this.accountInId = accountInId;
    }
}
