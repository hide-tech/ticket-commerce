package com.yazykov.ticketservice.model;

import com.yazykov.ticketservice.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    private Long id;
    private String name;
    private EventType type;
    private LocalDateTime dateTime;
    private Set<Seat> seats;
    @Version
    private int version;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    public Event(EventDto eventDto) {
        this.name = eventDto.name();
        this.type = eventDto.type();
        this.dateTime = eventDto.dateTime();
        version = 0;
    }
}
