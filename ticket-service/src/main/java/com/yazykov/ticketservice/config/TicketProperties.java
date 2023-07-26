package com.yazykov.ticketservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "ticket")
public class TicketProperties {

    private String greeting;


}
