package com.transfer.app7b.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private LocalDateTime date = LocalDateTime.now();
    @JsonProperty
    private BigDecimal amount;
    @JsonProperty
    private String currency;
    @JsonProperty
    private String accountOutId;
    @JsonProperty
    private String accountInId;
}
