package com.engine.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("directoryWritability")
public class DirectoryWritabilityHealthIndicator implements HealthIndicator {

    @Value("${app.health.check-directory:/tmp}")
    private String checkDirectory;

    @Override
    public Health health() {
        File directory = new File(checkDirectory);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                return Health.down()
                        .withDetail("directory", checkDirectory)
                        .withDetail("reason", "Directory does not exist and could not be created")
                        .build();
            }
        }

        if (!directory.canWrite()) {
            return Health.down()
                    .withDetail("directory", checkDirectory)
                    .withDetail("reason", "Directory is not writable")
                    .build();
        }

        return Health.up()
                .withDetail("directory", checkDirectory)
                .withDetail("writable", true)
                .build();
    }
}
