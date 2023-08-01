package com.yazykov.dispatchservice.dto;

public record OrderAcceptedDto(
        Long orderId,
        Long eventId,
        Long seatId
) {
}
