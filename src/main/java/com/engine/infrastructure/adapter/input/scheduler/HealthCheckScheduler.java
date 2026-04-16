package com.engine.infrastructure.adapter.input.scheduler;

import com.engine.aplication.port.input.HealthCheckUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class HealthCheckScheduler {
    private final HealthCheckUseCase healthCheckUseCase;

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void checkHealthOfServices() {
        log.info("Starting automated health check execution...");
        
        try {
            healthCheckUseCase.checkAllServicesHealth();
            log.info("Health check execution completed successfully.");
        } catch (Exception e) {
            log.error("Critical error during automated health check: {}", e.getMessage());
        }
    }
}

