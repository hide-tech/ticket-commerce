package com.yazykov.ticketservice.controller;

import com.yazykov.ticketservice.config.TicketProperties;
import com.yazykov.ticketservice.dto.EventDto;
import com.yazykov.ticketservice.model.EventType;
import com.yazykov.ticketservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final TicketProperties ticketProperties;

    @GetMapping("/")
    public String greeting() {
        return ticketProperties.getGreeting();
    }

    @GetMapping("/events/all")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> getAllEventsByDateTimeAndType(@RequestParam("dateTime") String dateTime,
                                                                        @RequestParam(name = "type", required = false) EventType type) {
        return ResponseEntity.ok(eventService.getAllEvents(dateTime, type));
    }

    @PostMapping("/events")
    public ResponseEntity<EventDto> createNewEvent(@RequestBody EventDto eventDto) {
        return ResponseEntity.of(Optional.of(eventService.addNewEvent(eventDto)));
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
        return ResponseEntity.of(Optional.of(eventService.updateEvent(id, eventDto)));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
