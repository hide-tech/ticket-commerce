package com.yazykov.orderservice.dto;

import com.yazykov.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record OrderDto(
        Long id,
        @NotNull(message = "The event id must be defined.")
        Long eventId,
        @NotNull(message = "The seat id must be defined.")
        Long seatId,
        @NotNull(message = "The event name must be defined.")
        String eventName,
        @NotBlank(message = "The seat sector must be defined.")
        String seatSector,
        @NotBlank(message = "The seat line must be defined.")
        String seatLine,
        @NotBlank(message = "The seat place must be defined.")
        String seatPlace,
        @NotNull(message = "Event date and time should not be null")
        LocalDateTime eventDateTime,
        BigDecimal price,
        OrderStatus orderStatus,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
