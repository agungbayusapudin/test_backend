package com.engine.aplication.port.output;

import com.engine.domain.model.HealthCheckHistory;

public interface HealthCheckRepositoryPort {
    void save(HealthCheckHistory history);
}