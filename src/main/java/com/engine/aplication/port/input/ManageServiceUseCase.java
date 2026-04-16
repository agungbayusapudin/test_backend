package com.engine.aplication.port.input;

import com.engine.domain.model.ManagedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManageServiceUseCase {
    Page<ManagedService> getAllServices(Pageable pageable);
    ManagedService getServiceById(Long id);
    ManagedService createService(ManagedService service);
    ManagedService updateService(Long id, ManagedService service);
    void deleteService(Long id);
}
