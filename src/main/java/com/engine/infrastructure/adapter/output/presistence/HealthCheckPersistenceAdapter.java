package com.engine.infrastructure.adapter.output.presistence;

import com.engine.aplication.port.output.HealthCheckRepositoryPort;
import com.engine.domain.model.HealthCheckHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HealthCheckPersistenceAdapter implements HealthCheckRepositoryPort {

    private final HealthChecksHistoryRepository repository;

    @Override
    public void save(HealthCheckHistory history) {
        repository.save(history);
    }
}
