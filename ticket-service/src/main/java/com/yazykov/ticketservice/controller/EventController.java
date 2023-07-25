package com.yazykov.ticketservice.controller;

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

    @GetMapping("/")
    public String greeting() {
        return "Welcome at our ticket service";
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDto>> getAllEventsByDateTimeAndType(@RequestParam("dateTime") String dateTime,
                                                                        @RequestParam("type") EventType type) {
        return ResponseEntity.of(Optional.ofNullable(eventService.getAllEvents(dateTime, type)));
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
