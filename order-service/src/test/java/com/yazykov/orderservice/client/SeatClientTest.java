package com.yazykov.orderservice.client;

import com.yazykov.orderservice.dto.SeatDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

class SeatClientTest {

    private MockWebServer mockWebServer;
    private SeatClient seatClient;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        var webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").uri().toString())
                .build();
        this.seatClient = new SeatClient(webClient);
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void getSeatByIdAndEventId_seatReturned() {
        var seatId = 17L;
        var eventId = 14L;
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                            "id": %s,
                            "sector": "VIP",
                            "line": "4",
                            "place": "5",
                            "type": "VIP",
                            "state": "FREE",
                            "price": 15.50,
                            "eventId": %s
                        }
                        """.formatted(seatId, eventId));
        mockWebServer.enqueue(mockResponse);
        Mono<SeatDto> seatDtoMono = seatClient.getSeatByIdAndEventId(seatId, eventId);

        StepVerifier.create(seatDtoMono)
                .expectNextMatches(s -> s.eventId().equals(eventId))
                .verifyComplete();
    }
}