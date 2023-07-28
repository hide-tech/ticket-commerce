package com.yazykov.ticketservice.service;

import com.yazykov.ticketservice.dto.EventDto;
import com.yazykov.ticketservice.mapper.SeatMapper;
import com.yazykov.ticketservice.model.Event;
import com.yazykov.ticketservice.model.EventType;
import com.yazykov.ticketservice.model.SeatState;
import com.yazykov.ticketservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final SeatService seatService;
    private final SeatMapper seatMapper;

    public List<EventDto> getAll() {
        return eventRepository.findAll().stream().map(event -> {
            return new EventDto(
                    event.getId(),
                    event.getName(),
                    event.getType(),
                    event.getDateTime(),
                    event.getSeats().stream().map(seatMapper::seatToSeatDto).collect(Collectors.toSet()),
                    event.getCreatedDate(),
                    event.getLastModifiedDate()
            );
        }).collect(Collectors.toList());
    }

    public List<EventDto> getAllEvents(String dateTime, EventType type) {
        LocalDateTime instant = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
        List<EventDto> eventDtos = eventRepository.findAllByDateTime(instant)
                .stream().map(event -> {
                    return new EventDto(
                            event.getId(),
                            event.getName(),
                            event.getType(),
                            event.getDateTime(),
                            event.getSeats().stream().map(seatMapper::seatToSeatDto).collect(Collectors.toSet()),
                            event.getCreatedDate(),
                            event.getLastModifiedDate()
                    );
                }).collect(Collectors.toList());
        if (type == null)
            return eventDtos;
        else
            return eventDtos.stream().filter(el -> Objects.equals(el.type(), type)).collect(Collectors.toList());
    }

    public EventDto updateEvent(Long id, EventDto eventDto) {
        Event updatedEvent = eventRepository.findById(id).map(existingEvent -> {
            var eventToUpdate = new Event(
                    existingEvent.getId(),
                    eventDto.name(),
                    eventDto.type(),
                    eventDto.dateTime(),
                    existingEvent.getSeats(),
                    existingEvent.getVersion(),
                    existingEvent.getCreatedDate(),
                    Instant.now()
            );
            return eventRepository.save(eventToUpdate);
        }).orElseGet(() -> {
            var newEvent = new Event(eventDto);
            newEvent.setSeats(seatService.initEmptySeats(newEvent));
            return eventRepository.save(newEvent);
        });
        return new EventDto(updatedEvent.getId(),
                updatedEvent.getName(),
                updatedEvent.getType(),
                updatedEvent.getDateTime(),
                updatedEvent.getSeats().stream().map(seatMapper::seatToSeatDto).collect(Collectors.toSet()),
                updatedEvent.getCreatedDate(),
                updatedEvent.getLastModifiedDate());
    }

    public EventDto addNewEvent(EventDto eventDto) {
        var newEvent = new Event(eventDto);
        newEvent.setSeats(seatService.initEmptySeats(newEvent));
        Event save = eventRepository.save(newEvent);
        return new EventDto(save.getId(),
                save.getName(),
                save.getType(),
                save.getDateTime(),
                save.getSeats().stream().map(seatMapper::seatToSeatDto).collect(Collectors.toSet()),
                save.getCreatedDate(),
                save.getLastModifiedDate());
    }

    public void deleteEvent(Long id) {
        eventRepository.findById(id).get().getSeats().stream()
                .filter(el -> Objects.equals(el.getState(), SeatState.RESERVED)).forEach(el -> {
                    el.setState(SeatState.FEE_RETURN);
                    seatService.saveSeat(el);
                });
        eventRepository.deleteById(id);
    }
}
