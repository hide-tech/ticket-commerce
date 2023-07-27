package com.yazykov.orderservice.dto;

import com.yazykov.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record OrderDto(
        Long id,
        @NotBlank(message = "The event id must be defined.")
        Long eventId,
        @NotBlank(message = "The seat id must be defined.")
        Long seatId,
        @NotNull(message = "The event name must be defined.")
        String eventName,
        @NotBlank(message = "The seat sector must be defined.")
        String seatSector,
        @NotBlank(message = "The seat line must be defined.")
        String seatLine,
        @NotBlank(message = "The seat place must be defined.")
        String seatPlace,
        @NotBlank(message = "The event time must be defined.")
        LocalDateTime eventDateTime,
        BigDecimal price,
        OrderStatus orderStatus,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
