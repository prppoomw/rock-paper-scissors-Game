package com.example.producer.component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppMetric {

    private final Counter playRequestCounter;

    public AppMetric(MeterRegistry meterRegistry) {
        this.playRequestCounter = Counter.builder("rps.play.request")
                .description("Number of play api request")
                .register(meterRegistry);
    }

    public void increasePlayRequestCounter() {
        playRequestCounter.increment();
    }
}
