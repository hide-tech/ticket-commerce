package com.yazykov.ticketservice;

import com.yazykov.ticketservice.dto.EventDto;
import com.yazykov.ticketservice.model.EventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class TicketServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createNewEventAndGetThisEventByDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 12, 17, 30);
        String instant = "2023-05-12.17:30:00";
        EventDto eventDto = new EventDto("New concert", EventType.CONCERT, dateTime, null, null, null);
        EventDto expectedEvent = webTestClient
                .post()
                .uri("/events")
                .bodyValue(eventDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(EventDto.class).value(Assertions::assertNotNull)
                .returnResult().getResponseBody();

        webTestClient.get()
                .uri("/events?dateTime=" + instant)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(List.class).value(actualEvent -> {
                    Assertions.assertNotNull(actualEvent);
                    Assertions.assertEquals(1, actualEvent.size());
                });
    }

}
