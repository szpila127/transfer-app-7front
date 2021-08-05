package com.transfer.app7f.config;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class JsonBuilder<T> {

    public HttpEntity<String> prepareJson(T dto) {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent, headers);
        return httpEntity;
    }
}
