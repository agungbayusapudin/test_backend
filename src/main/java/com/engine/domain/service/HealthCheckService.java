package com.engine.domain.service;

import com.engine.aplication.port.input.HealthCheckUseCase;
import com.engine.aplication.port.output.HealthCheckRepositoryPort;
import com.engine.aplication.port.output.ManageServiceRepositoryPort;
import com.engine.domain.model.HealthCheckHistory;
import com.engine.domain.model.ManagedService;
import com.engine.infrastructure.adapter.output.presistence.HealthChecksHistoryRepository;
import com.engine.infrastructure.adapter.output.presistence.ManageServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckService implements HealthCheckUseCase {

    private final ManageServiceRepository serviceRepository;
    private final HealthChecksHistoryRepository historyRepository;
    private final HealthCheckRepositoryPort historyPort;

    @Value("${app.scheduler.timeout-seconds:5}")
    private int timeoutSeconds;

    @Override
    public Page<HealthCheckHistory> getHealthCheckHistory(Long serviceId, Pageable pageable) {
        return historyRepository.findByServiceIdOrderByCheckedAtDesc(serviceId, pageable);
    }

    @Override
    @Transactional
    public void checkAllServicesHealth() {
        List<ManagedService> services = serviceRepository.findAll();
        log.info("Running health check for {} services", services.size());

        for (ManagedService service : services) {
            checkService(service);
        }
    }

    private void checkService(ManagedService service) {
        long startTime = System.currentTimeMillis();
        String status;
        String errorMessage = null;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(service.getIpAddress(), service.getPort()),
                    timeoutSeconds * 1000);
            status = "UP";
        } catch (Exception e) {
            status = "DOWN";
            errorMessage = e.getMessage();
        }

        int responseTimeMs = (int) (System.currentTimeMillis() - startTime);

        // Update status di managed_services
        serviceRepository.updateStatusAndLastCheck(service.getId(), status, LocalDateTime.now());

        // Simpan ke history
        if ("DOWN".equals(status)) {
            // DOWN → selalu simpan
            historyPort.save(HealthCheckHistory.builder()
                    .service(service)
                    .status(status)
                    .responseTimeMs(responseTimeMs)
                    .errorMessage(errorMessage)
                    .build());
        } else {
            // UP → simpan hanya kalau belum ada record UP dalam 12 jam terakhir
            boolean recentUpExists = historyRepository.existsByServiceIdAndStatusAndCheckedAtAfter(
                    service.getId(), "UP", LocalDateTime.now().minusHours(1));
            if (!recentUpExists) {
                historyPort.save(HealthCheckHistory.builder()
                        .service(service)
                        .status(status)
                        .responseTimeMs(responseTimeMs)
                        .build());
            }
        }

        log.info("Service [{}] {}:{} → {} ({}ms)",
                service.getName(), service.getIpAddress(), service.getPort(), status, responseTimeMs);
    }
}
