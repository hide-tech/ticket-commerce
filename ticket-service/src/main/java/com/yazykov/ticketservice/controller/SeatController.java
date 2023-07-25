package com.yazykov.ticketservice.controller;

import com.yazykov.ticketservice.dto.SeatDto;
import com.yazykov.ticketservice.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/events/{eventId}/seats")
    public ResponseEntity<Set<SeatDto>> getAllSeatsByEvent(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(seatService.getAllSeats(eventId));
    }

    @PostMapping("/events/{eventId}/seats/{seatId}")
    public ResponseEntity<SeatDto> reserveSeat(@PathVariable("eventId") Long eventId,
                                               @PathVariable("seatId") Long seatId) {
        return ResponseEntity.ok(seatService.reserveSeat(eventId, seatId));
    }

    @PostMapping("/events/{eventId}/seats/{seatId}/block")
    public ResponseEntity<SeatDto> blockSeat(@PathVariable("eventId") Long eventId,
                                               @PathVariable("seatId") Long seatId) {
        return ResponseEntity.ok(seatService.blockSeat(eventId, seatId));
    }

    @PostMapping("/events/{eventId}/seats/{seatId}/return")
    public ResponseEntity<SeatDto> returnSeat(@PathVariable("eventId") Long eventId,
                                               @PathVariable("seatId") Long seatId) {
        return ResponseEntity.ok(seatService.returnSeat(eventId, seatId));
    }
}
