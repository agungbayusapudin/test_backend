package com.engine.infrastructure.adapter.output.presistence;

import com.engine.aplication.port.output.ManageServiceRepositoryPort;
import com.engine.domain.model.ManagedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManageServiceRepositoryImpl implements ManageServiceRepositoryPort {

    private final ManageServiceRepository repository;

    @Override
    public List<ManagedService> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ManagedService> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ManagedService save(ManagedService service) {
        return repository.save(service);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    public boolean existsByIpAddressAndPort(String ipAddress, Integer port) {
        return repository.existsByIpAddressAndPort(ipAddress, port);
    }
}
