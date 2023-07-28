package com.yazykov.orderservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "ticket")
public class WebClientProperties {

    @NotNull
    URI ticketServiceUrl;

    public URI getTicketServiceUrl() {
        return ticketServiceUrl;
    }

    public void setTicketServiceUrl(URI ticketServiceUrl) {
        this.ticketServiceUrl = ticketServiceUrl;
    }
}
