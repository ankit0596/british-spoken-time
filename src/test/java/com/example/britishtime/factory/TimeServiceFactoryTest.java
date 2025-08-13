package com.example.britishtime.factory;

import com.example.britishtime.service.TimeConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeServiceFactoryTest {

    private TimeConversionService britishService;
    private TimeConversionService usService;
    private TimeServiceFactory factory;

    @BeforeEach
    void setUp() {
        britishService = mock(TimeConversionService.class);
        usService = mock(TimeConversionService.class);

        Map<String, TimeConversionService> services = new HashMap<>();
        services.put("british", britishService);
        services.put("us", usService);

        factory = new TimeServiceFactory(services);
    }

    @Test
    @DisplayName("Should return correct service when locale key exists")
    void testGetServiceValidKey() {
        assertSame(britishService, factory.getService("british"));
        assertSame(usService, factory.getService("us"));
    }

    @Test
    @DisplayName("Should return null when locale key does not exist")
    void testGetServiceInvalidKey() {
        assertNull(factory.getService("australian"));
    }

    @Test
    @DisplayName("Should return null when locale key is null")
    void testGetServiceNullKey() {
        assertNull(factory.getService(null));
    }

    @Test
    @DisplayName("Should handle empty service map gracefully")
    void testEmptyServiceMap() {
        TimeServiceFactory emptyFactory = new TimeServiceFactory(new HashMap<>());
        assertNull(emptyFactory.getService("british"));
    }

    @Test
    @DisplayName("Multiple calls should consistently return same service instances")
    void testConsistencyAcrossCalls() {
        TimeConversionService service1 = factory.getService("british");
        TimeConversionService service2 = factory.getService("british");
        assertSame(service1, service2);
    }

    @Test
    @DisplayName("Keys should be case-sensitive by default")
    void testCaseSensitivity() {
        assertNull(factory.getService("British")); // different case
        assertNotNull(factory.getService("british")); // exact match
    }

    @Test
    @DisplayName("Map reference should be the one provided in constructor (immutability assumption test)")
    void testMapReference() {
        Map<String, TimeConversionService> mapRef = new HashMap<>();
        mapRef.put("british", britishService);

        TimeServiceFactory customFactory = new TimeServiceFactory(mapRef);
        assertSame(mapRef.get("british"), customFactory.getService("british"));
    }
}
