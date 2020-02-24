package com.transfer.app7f.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppEventDto {
    private Long id;
    private String date;
    private String event;
    private String information;

    public Long getId() {
        return id;
    }

    public String getDate() {
        String newDate = date.replace('T', ' ');
        return newDate;
    }

    public String getEvent() {
        return event;
    }

    public String getInformation() {
        return information;
    }
}

