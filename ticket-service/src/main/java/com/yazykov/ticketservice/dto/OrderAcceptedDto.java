package com.yazykov.ticketservice.dto;

public record OrderAcceptedDto(
        Long orderId,
        Long eventId,
        Long seatId
) {
}
