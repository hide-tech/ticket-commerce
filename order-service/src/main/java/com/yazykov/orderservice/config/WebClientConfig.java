package com.yazykov.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClientProperties webClientProperties, WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(webClientProperties.getTicketServiceUrl().toString()).build();
    }
}
