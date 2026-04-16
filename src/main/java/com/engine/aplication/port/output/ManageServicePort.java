package com.engine.aplication.port.output;

import com.engine.domain.model.ManagedService;

import java.util.List;
import java.util.Optional;

public interface ManageServiceRepositoryPort {
    List<ManagedService> findAll();
    Optional<ManagedService> findById(Long id);
    ManagedService save(ManagedService service);
    void deleteById(Long id);
}
