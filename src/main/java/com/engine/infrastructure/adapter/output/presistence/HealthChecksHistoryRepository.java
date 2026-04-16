package com.engine.infrastructure.adapter.output.presistence;

import com.engine.domain.model.HealthCheckHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface HealthChecksHistoryRepository extends JpaRepository<HealthCheckHistory, Long> {
    Page<HealthCheckHistory> findByServiceIdOrderByCheckedAtDesc(Long serviceId, Pageable pageable);

    boolean existsByServiceIdAndStatusAndCheckedAtAfter(Long serviceId, String status, LocalDateTime after);

    @Modifying
    @Transactional
    @Query("DELETE FROM HealthCheckHistory h WHERE h.checkedAt < :cutoff")
    void deleteOlderThan(@Param("cutoff") LocalDateTime cutoff);
}