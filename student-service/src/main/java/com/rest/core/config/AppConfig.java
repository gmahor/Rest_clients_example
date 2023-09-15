package com.rest.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/v1/schools").build();
    }

}
