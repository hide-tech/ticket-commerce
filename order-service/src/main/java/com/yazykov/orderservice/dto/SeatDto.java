package com.yazykov.orderservice.dto;

import com.yazykov.orderservice.model.SeatState;
import com.yazykov.orderservice.model.SeatType;

import java.math.BigDecimal;

public record SeatDto (
        Long id,
        String sector,
        String line,
        String place,
        SeatType type,
        SeatState state,
        BigDecimal price
) {
}
