package com.yazykov.orderservice.dto;

public record OrderDispatchedDto(
        Long orderId,
        Long eventId,
        Long seatId
) {
}
