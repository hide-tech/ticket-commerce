package com.yazykov.orderservice.client;

import com.yazykov.orderservice.dto.SeatDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
                .bodyToMono(SeatDto.class)
                .timeout(Duration.of(3, ChronoUnit.SECONDS), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(Exception.class, ex -> Mono.empty());
    }
}
