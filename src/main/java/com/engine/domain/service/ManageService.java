package com.engine.domain.service;

import com.engine.aplication.port.input.ManageServiceUseCase;
import com.engine.aplication.port.output.ManageServiceRepositoryPort;
import com.engine.domain.exception.Exception;
import com.engine.domain.model.ManagedService;
import com.engine.infrastructure.adapter.output.presistence.ManageServicePersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManageService implements ManageServiceUseCase {

    private final ManageServiceRepositoryPort repository;
    private final ManageServicePersistenceAdapter persistenceAdapter;

    @Override
    public Page<ManagedService> getAllServices(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ManagedService getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new Exception.ServiceNotFoundException(id));
    }

    @Override
    @Transactional
    public ManagedService createService(ManagedService service) {
        if (persistenceAdapter.existsByNameIgnoreCase(service.getName())) {
            throw new Exception.ServiceNameAlreadyExistsException(service.getName());
        }
        if (persistenceAdapter.existsByIpAddressAndPort(service.getIpAddress(), service.getPort())) {
            throw new Exception.ServiceAddressAlreadyExistsException(service.getIpAddress(), service.getPort());
        }
        return repository.save(service);
    }

    @Override
    @Transactional
    public ManagedService updateService(Long id, ManagedService updated) {
        ManagedService existing = getServiceById(id);
        if (updated.getName() != null) existing.setName(updated.getName());
        if (updated.getIpAddress() != null) existing.setIpAddress(updated.getIpAddress());
        if (updated.getPort() != null) existing.setPort(updated.getPort());
        if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        getServiceById(id);
        repository.deleteById(id);
    }
}
