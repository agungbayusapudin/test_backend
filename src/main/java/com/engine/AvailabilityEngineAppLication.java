package com.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AvailabilityEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvailabilityEngineApplication.class, args);
    }
}
