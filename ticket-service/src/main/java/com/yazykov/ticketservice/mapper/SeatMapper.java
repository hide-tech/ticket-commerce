package com.yazykov.ticketservice.mapper;

import com.yazykov.ticketservice.dto.SeatDto;
import com.yazykov.ticketservice.model.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class SeatMapper {

    public SeatDto seatToSeatDto(Seat seat) {
        return new SeatDto(
                seat.getId(),
                seat.getSector(),
                seat.getLine(),
                seat.getPlace(),
                seat.getType(),
                seat.getState(),
                seat.getPrice(),
                seat.getEvent().getId()
        );
    }
}
