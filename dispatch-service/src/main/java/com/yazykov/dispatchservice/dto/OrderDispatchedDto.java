package com.yazykov.dispatchservice.dto;

public record OrderDispatchedDto(
        Long orderId,
        Long eventId,
        Long seatId
) {
}
