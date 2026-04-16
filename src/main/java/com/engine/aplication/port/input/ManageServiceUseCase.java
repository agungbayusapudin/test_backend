package com.engine.aplication.port.input;

import com.engine.domain.model.ManagedService;

import java.util.List;

public interface ManageServiceUseCase {
    List<ManagedService> getAllServices();
    ManagedService getServiceById(Long id);
    ManagedService createService(ManagedService service);
    ManagedService updateService(Long id, ManagedService service);
    void deleteService(Long id);
}
