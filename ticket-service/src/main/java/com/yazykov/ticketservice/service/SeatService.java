package com.yazykov.ticketservice.service;

import com.yazykov.ticketservice.dto.SeatDto;
import com.yazykov.ticketservice.mapper.SeatMapper;
import com.yazykov.ticketservice.model.Event;
import com.yazykov.ticketservice.model.Seat;
import com.yazykov.ticketservice.model.SeatState;
import com.yazykov.ticketservice.model.SeatType;
import com.yazykov.ticketservice.repository.EventRepository;
import com.yazykov.ticketservice.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;
    private final SeatMapper seatMapper;

    public Set<Seat> initEmptySeats(Event event) {
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat(null, "A", "1", "1", SeatType.TYPE_A, SeatState.FREE, new BigDecimal("15"),event));
        seats.add(new Seat(null, "B", "1", "1", SeatType.TYPE_A, SeatState.FREE, new BigDecimal("15"),event));
        seats.add(new Seat(null, "C", "1", "1", SeatType.TYPE_A, SeatState.FREE, new BigDecimal("15"),event));
        seats.add(new Seat(null, "A", "2", "1", SeatType.TYPE_B, SeatState.FREE, new BigDecimal("12"),event));
        seats.add(new Seat(null, "B", "2", "1", SeatType.TYPE_B, SeatState.FREE, new BigDecimal("12"),event));
        seats.add(new Seat(null, "C", "2", "1", SeatType.TYPE_B, SeatState.FREE, new BigDecimal("12"),event));
        seats.add(new Seat(null, "A", "3", "1", SeatType.TYPE_C, SeatState.FREE, new BigDecimal("10"),event));
        seats.add(new Seat(null, "B", "3", "1", SeatType.TYPE_C, SeatState.FREE, new BigDecimal("10"),event));
        seats.add(new Seat(null, "C", "3", "1", SeatType.TYPE_C, SeatState.FREE, new BigDecimal("10"),event));
        seats.add(new Seat(null, "VIP", "1", "1", SeatType.VIP, SeatState.FREE, new BigDecimal("25"),event));
        seats.add(new Seat(null, "VIP", "1", "2", SeatType.VIP, SeatState.FREE, new BigDecimal("25"),event));
        seats.add(new Seat(null, "VIP", "1", "3", SeatType.VIP, SeatState.FREE, new BigDecimal("25"),event));
        return seats;
    }

    public void saveSeat(Seat seat) {
        seatRepository.save(seat);
    }

    public Set<SeatDto> getAllSeats(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id doesn't exist"));
        return event.getSeats().stream().map(seatMapper::seatToSeatDto).collect(Collectors.toSet());
    }

    public SeatDto getSeatById(Long eventId, Long seatId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id doesn't exist"));
        SeatDto result = event.getSeats().stream().filter(el -> Objects.equals(el.getId(), seatId)).findFirst()
                .map(seatMapper::seatToSeatDto)
                .orElseThrow(() -> new RuntimeException("Seat with id doesn't exist"));
        if (!result.state().equals(SeatState.FREE))
            throw new RuntimeException("Seat already not available");
        return result;
    }

    public SeatDto reserveSeat(Long eventId, Long seatId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id doesn't exist"));
        Optional<Seat> result = event.getSeats().stream().filter(el -> Objects.equals(el.getId(), seatId))
                .findFirst();
        if (result.isEmpty())
            throw new RuntimeException(String.format("Seat with id %s not exist", seatId));
        if (!result.get().getState().equals(SeatState.FREE))
            throw new RuntimeException(String.format("Seat with id %s not available", seatId));
        Seat seat = result.get();
        seat.setState(SeatState.RESERVED);
        seat = seatRepository.save(seat);
        return seatMapper.seatToSeatDto(seat);
    }

    public SeatDto blockSeat(Long eventId, Long seatId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id doesn't exist"));
        Optional<Seat> result = event.getSeats().stream().filter(el -> Objects.equals(el.getId(), seatId))
                .findFirst();
        if (result.isEmpty())
            throw new RuntimeException(String.format("Seat with id %s not exist", seatId));
        if (!result.get().getState().equals(SeatState.FREE))
            throw new RuntimeException(String.format("Seat with id %s already reserved", seatId));
        Seat seat = result.get();
        seat.setState(SeatState.UNAVAILABLE);
        seat = seatRepository.save(seat);
        return seatMapper.seatToSeatDto(seat);
    }

    public SeatDto returnSeat(Long eventId, Long seatId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id doesn't exist"));
        Optional<Seat> result = event.getSeats().stream().filter(el -> Objects.equals(el.getId(), seatId))
                .findFirst();
        if (result.isEmpty())
            throw new RuntimeException(String.format("Seat with id %s not exist", seatId));
        if (!result.get().getState().equals(SeatState.RESERVED))
            throw new RuntimeException(String.format("Seat with id %s not reserved", seatId));
        Seat seat = result.get();
        seat.setState(SeatState.FREE);
        seat = seatRepository.save(seat);
        return seatMapper.seatToSeatDto(seat);
    }
}
