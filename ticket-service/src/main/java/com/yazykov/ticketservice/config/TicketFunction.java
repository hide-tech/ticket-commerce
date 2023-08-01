package com.yazykov.ticketservice.config;

import com.yazykov.ticketservice.dto.OrderAcceptedDto;
import com.yazykov.ticketservice.service.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class TicketFunction {

    @Bean
    public Consumer<OrderAcceptedDto> reserveOrder(EventService eventService) {
        return eventService::processReserveSeat;
    }
}
