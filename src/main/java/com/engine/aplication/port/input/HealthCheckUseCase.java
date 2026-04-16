package com.engine.aplication.port.input;

import com.engine.domain.model.HeathCheckHisotry;
import java.util.List;

public interface HealthCheckUseCase {
    List<HeathCheckHisotry> getHealthCheckHistory(Long serviceId);
    void checkAllServicesHealth();
}