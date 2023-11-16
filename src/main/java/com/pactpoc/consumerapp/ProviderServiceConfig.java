package com.pactpoc.consumerapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProviderServiceConfig {

    @Bean
    RestTemplate productRestTemplate(@Value("${provider.port:8080}") int port) {
        System.out.println("sout");
        return new RestTemplateBuilder().rootUri(String.format("http://localhost:%d", port)).build();
    }
}

