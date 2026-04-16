package com.engine;


import os.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class AvailabilityEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvailabilityEngineApplication.class, args);
    }
}