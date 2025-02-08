package com.example.consumer.component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppMetric {

    private final Counter gameHistoryRequestCounter;
    private final Counter gameScoreRequestCounter;
    private final Counter resetGameRequestCounter;

    public AppMetric(MeterRegistry meterRegistry) {
        this.gameHistoryRequestCounter = Counter.builder("rps.game-history.request")
                .description("Number of game history api request")
                .register(meterRegistry);
        this.gameScoreRequestCounter = Counter.builder("rps.game-score.request")
                .description("Number of game score api request")
                .register(meterRegistry);
        this.resetGameRequestCounter = Counter.builder("rps.reset-game.request")
                .description("Number of resetting game api request")
                .register(meterRegistry);
    }

    public void increaseGameHistoryRequestCounter() {
        gameHistoryRequestCounter.increment();
    }

    public void increaseGameScoreRequestCounter() {
        gameScoreRequestCounter.increment();
    }

    public void increaseResetGameRequestCounter() {
        resetGameRequestCounter.increment();
    }
}
