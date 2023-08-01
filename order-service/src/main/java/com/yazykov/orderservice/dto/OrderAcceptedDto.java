package com.yazykov.orderservice.dto;

public record OrderAcceptedDto(
        Long orderId,
        Long eventId,
        Long seatId
) {
}
