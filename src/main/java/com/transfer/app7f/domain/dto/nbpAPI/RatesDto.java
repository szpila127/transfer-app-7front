package com.transfer.app7f.domain.dto.nbpAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesDto {

    @JsonProperty
    private double mid;
}