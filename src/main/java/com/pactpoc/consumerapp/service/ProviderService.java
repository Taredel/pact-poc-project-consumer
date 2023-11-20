package com.pactpoc.consumerapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pactpoc.consumerapp.dto.SimpleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProviderService {

    private String url = "http://localhost:8080";

    @Autowired
    private RestTemplate restTemplate;

    public SimpleDto callProvider(int queryParameter) {
        System.out.println("sout");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .path("/getSomething")
                .queryParam("inputData", queryParameter)
                .encode()
                .toUriString();
        String response;
        try {
            response = restTemplate.getForObject(urlTemplate, String.class);
        } catch (HttpServerErrorException.InternalServerError ex) {
            return null;
        }
        Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.fromJson(response, SimpleDto.class);
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
