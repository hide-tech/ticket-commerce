package com.yazykov.ticketservice.dto;

import com.yazykov.ticketservice.model.SeatState;
import com.yazykov.ticketservice.model.SeatType;

import java.math.BigDecimal;

public record SeatDto(
        Long id,
        String sector,
        String line,
        String place,
        SeatType type,
        SeatState state,
        BigDecimal price
) {
}
