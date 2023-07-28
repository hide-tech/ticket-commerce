package com.yazykov.orderservice.client;

import com.yazykov.orderservice.dto.SeatDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SeatClient {
    private final WebClient webClient;

    public SeatClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<SeatDto> getSeatByIdAndEventId(Long seatId, Long eventId) {
        return webClient
                .get()
                .uri("/events/" + eventId + "/seats/" + seatId)
                .retrieve()
                .bodyToMono(SeatDto.class);
    }
}
