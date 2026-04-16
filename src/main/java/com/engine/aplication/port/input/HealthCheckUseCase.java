package com.engine.aplication.port.input;

import com.engine.domain.model.HealthCheckHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HealthCheckUseCase {
    Page<HealthCheckHistory> getHealthCheckHistory(Long serviceId, Pageable pageable);
    void checkAllServicesHealth();
}
