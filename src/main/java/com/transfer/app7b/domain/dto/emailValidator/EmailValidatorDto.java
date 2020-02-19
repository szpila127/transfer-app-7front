package com.transfer.app7b.domain.dto.emailValidator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class EmailValidatorDto {

    @JsonProperty(value = "isValid")
    private boolean valid;
}
