package com.example.britishtime.service.impl;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.factory.TimePhraseStrategyFactory;
import com.example.britishtime.strategy.TimePhraseStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BritishTimeServiceImplTest {

    @Mock
    private TimePhraseStrategyFactory strategyFactory;

    @Mock
    private TimePhraseStrategy mockStrategy;

    private BritishTimeServiceImpl britishTimeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        britishTimeService = new BritishTimeServiceImpl(strategyFactory);
    }


    private Stream<Arguments> timeScenarios() {
        return Stream.of(
                Arguments.of(0, 0, "midnight"),
                Arguments.of(12, 0, "noon"),
                Arguments.of(10, 0, "ten o'clock"),
                Arguments.of(10, 30, "half past ten"),
                Arguments.of(10, 15, "quarter past ten"),
                Arguments.of(10, 29, "twenty nine past ten"),
                Arguments.of(10, 31, "twenty nine to eleven"),
                Arguments.of(23, 59, "one to midnight")
        );
    }

    @DisplayName("Should correctly delegate to strategy for different time scenarios")
    @ParameterizedTest(name = "Given {0}:{1} should return \"{2}\"")
    @MethodSource("timeScenarios")
    void testToSpokenTime_UsesCorrectStrategy(int hour, int minute, String expectedPhrase) {
        // Arrange
        LocalTime time = LocalTime.of(hour, minute);
        SpokenTimeResponse expectedResponse = new SpokenTimeResponse(time.toString(), expectedPhrase);

        // Generic stubbing so any hour/minute gets the same mockStrategy
        when(strategyFactory.getStrategy(anyInt(), anyInt())).thenReturn(mockStrategy);
        when(mockStrategy.toWords(anyInt(), anyInt())).thenReturn(expectedResponse);

        // Act
        SpokenTimeResponse actualResponse = britishTimeService.toSpokenTime(time);

        // Assert
        assertEquals(expectedPhrase, actualResponse.getSpokenTime());
        verify(strategyFactory).getStrategy(hour, minute);
        verify(mockStrategy).toWords(hour, minute);
    }

    private Stream<Arguments> invalidTimes() {
        return Stream.of(
                Arguments.of((LocalTime) null)
        );
    }

    @DisplayName("Should throw NullPointerException for invalid inputs")
    @ParameterizedTest(name = "Given time={0} should throw NPE")
    @MethodSource("invalidTimes")
    void testToSpokenTime_InvalidInputs(LocalTime time) {
        assertThrows(NullPointerException.class, () -> britishTimeService.toSpokenTime(time));
    }
}
