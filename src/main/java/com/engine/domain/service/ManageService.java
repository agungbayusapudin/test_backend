package com.engine.domain.service;

import com.engine.aplication.port.input.ManageServiceUseCase;
import com.engine.aplication.port.output.ManageServiceRepositoryPort;
import com.engine.domain.exception.Exception;
import com.engine.domain.model.ManagedService;
import com.engine.infrastructure.adapter.output.presistence.ManageServiceRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageService implements ManageServiceUseCase {

    private final ManageServiceRepositoryPort repository;
    private final ManageServiceRepositoryImpl repositoryImpl;

    @Override
    public List<ManagedService> getAllServices() {
        return repository.findAll();
    }

    @Override
    public ManagedService getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new Exception.ServiceNotFoundException(id));
    }

    @Override
    @Transactional
    public ManagedService createService(ManagedService service) {
        if (repositoryImpl.existsByNameIgnoreCase(service.getName())) {
            throw new Exception.ServiceNameAlreadyExistsException(service.getName());
        }
        if (repositoryImpl.existsByIpAddressAndPort(service.getIpAddress(), service.getPort())) {
            throw new Exception.ServiceAddressAlreadyExistsException(service.getIpAddress(), service.getPort());
        }
        return repository.save(service);
    }

    @Override
    @Transactional
    public ManagedService updateService(Long id, ManagedService updated) {
        ManagedService existing = getServiceById(id);
        existing.setName(updated.getName());
        existing.setIpAddress(updated.getIpAddress());
        existing.setPort(updated.getPort());
        existing.setDescription(updated.getDescription());
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        getServiceById(id);
        repository.deleteById(id);
    }
}
