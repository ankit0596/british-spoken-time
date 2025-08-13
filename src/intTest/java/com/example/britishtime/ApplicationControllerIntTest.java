package com.example.britishtime;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.factory.TimeServiceFactory;
import com.example.britishtime.service.TimeConversionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationControllerIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Default stub config for happy path tests.
     */
    @TestConfiguration
    static class StubConfig {
        @Bean
        public TimeServiceFactory timeServiceFactory() {
            TimeConversionService stubService = new TimeConversionService() {
                @Override
                public SpokenTimeResponse toSpokenTime(LocalTime time) {
                    return new SpokenTimeResponse(time.toString(), "Stubbed spoken time for " + time);
                }
            };
            return new TimeServiceFactory(Map.of("british", stubService));
        }
    }

    @Test
    @DisplayName("Valid time returns 200 with correct SpokenTimeResponseDto")
    void validTime_returnsOkResponse() {
        ResponseEntity<SpokenTimeResponse> response =
                restTemplate.getForEntity("/api/time/spoken?time=10:15", SpokenTimeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getInput()).isEqualTo("10:15");
        assertThat(response.getBody().getSpokenTime()).isEqualTo("Stubbed spoken time for 10:15");
    }

    @Test
    @DisplayName("Edge times like 00:00 and 23:59 return stubbed responses")
    void edgeTimes_returnStubbedResponses() {
        ResponseEntity<SpokenTimeResponse> midnightResp =
                restTemplate.getForEntity("/api/time/spoken?time=00:00", SpokenTimeResponse.class);
        assertThat(midnightResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(midnightResp.getBody()).isNotNull();
        assertThat(midnightResp.getBody().getSpokenTime()).isEqualTo("Stubbed spoken time for 00:00");

        ResponseEntity<SpokenTimeResponse> lastMinuteResp =
                restTemplate.getForEntity("/api/time/spoken?time=23:59", SpokenTimeResponse.class);
        assertThat(lastMinuteResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(lastMinuteResp.getBody()).isNotNull();
        assertThat(lastMinuteResp.getBody().getSpokenTime()).isEqualTo("Stubbed spoken time for 23:59");
    }

    @Test
    @DisplayName("Invalid time format returns 400 with Error DTO")
    void invalidTime_returnsBadRequestWithErrorDto() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/time/spoken?time=99:99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("\"error\":\"BAD_REQUEST\"");
        assertThat(response.getBody()).contains("\"message\"");
    }

    /**
     * Nested test class for error simulation scenario.
     * Uses a different bean configuration to simulate a service failure.
     */
    @Nested
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    class ServiceThrowsScenarioTest {

        @TestConfiguration
        static class ThrowingConfig {
            @Bean
            public TimeServiceFactory timeServiceFactory() {
                TimeConversionService throwingService = new TimeConversionService() {
                    @Override
                    public SpokenTimeResponse toSpokenTime(LocalTime time) {
                        throw new RuntimeException("Simulated failure");
                    }
                };
                return new TimeServiceFactory(Map.of("british", throwingService));
            }
        }

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        @DisplayName("Service throws exception -> 500 with Error DTO")
        void serviceThrows_returnsInternalServerError() {
            ResponseEntity<String> response =
                    restTemplate.getForEntity("/api/time/spoken?time=10:15", String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(response.getBody()).contains("\"error\":\"INTERNAL_ERROR\"");
            assertThat(response.getBody()).contains("Unexpected error");
        }
    }
}
