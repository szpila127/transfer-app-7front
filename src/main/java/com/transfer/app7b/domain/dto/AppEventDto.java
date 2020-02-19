package com.transfer.app7b.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppEventDto {
    private long id;
    private String date;
    private String event;
    private String information;

    public AppEventDto(String event, String information) {
        this.event = event;
        this.information = information;
    }
}