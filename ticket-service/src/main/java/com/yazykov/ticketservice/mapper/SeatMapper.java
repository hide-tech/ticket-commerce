package com.yazykov.ticketservice.mapper;

import com.yazykov.ticketservice.dto.SeatDto;
import com.yazykov.ticketservice.model.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {
    public SeatDto seatToSeatDto(Seat seat) {
        return new SeatDto(
                seat.getId(),
                seat.getSector(),
                seat.getLine(),
                seat.getPlace(),
                seat.getType(),
                seat.getState(),
                seat.getPrice(),
                null
        );
    }
}
