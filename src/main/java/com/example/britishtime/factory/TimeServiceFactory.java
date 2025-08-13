package com.example.britishtime.factory;

import com.example.britishtime.service.TimeConversionService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TimeServiceFactory {
    private final Map<String, TimeConversionService> services;

    public TimeServiceFactory(Map<String, TimeConversionService> services) {
        this.services = services;
    }

    public TimeConversionService getService(String localeKey) {
        return services.get(localeKey);
    }
}
