package com.yazykov.ticketservice.dto;

import com.yazykov.ticketservice.model.EventType;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

public record EventDto(
        Long id,
        @NotBlank(message = "Name of event should not be blank")
        String name,
        @NotBlank(message = "Type of event must be defined")
        EventType type,
        @NotBlank(message = "Date and time must be defined")
        LocalDateTime dateTime,
        Set<SeatDto> seats,
        Instant createdDate,
        Instant lastModifiedDate) {
        public static EventDto of(String name, EventType type, LocalDateTime dateTime, Set<SeatDto> seats) {
                return new EventDto(null, name, type, dateTime, seats, null, null);
        }
}
