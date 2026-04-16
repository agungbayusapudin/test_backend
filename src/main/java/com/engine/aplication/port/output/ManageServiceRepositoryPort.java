package com.engine.aplication.port.output;

import com.engine.domain.model.ManagedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ManageServiceRepositoryPort {
    Page<ManagedService> findAll(Pageable pageable);
    Optional<ManagedService> findById(Long id);
    ManagedService save(ManagedService service);
    void deleteById(Long id);
}
