package com.engine.infrastructure.adapter.output.presistence;

import com.engine.domain.model.ManagedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManageServiceRepository extends JpaRepository<ManagedService, Long> {

    List<ManagedService> findByCurrentStatus(String currentStatus);

    Optional<ManagedService> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByIpAddressAndPort(String ipAddress, Integer port);

    List<ManagedService> findByLastCheckAtIsNull();

    List<ManagedService> findByLastCheckAtBefore(LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query("UPDATE ManagedService s SET s.currentStatus = :status, s.lastCheckAt = :checkedAt WHERE s.id = :id")
    void updateStatusAndLastCheck(@Param("id") Long id,
                                  @Param("status") String status,
                                  @Param("checkedAt") LocalDateTime checkedAt);

    @Query("SELECT s.currentStatus, COUNT(s) FROM ManagedService s GROUP BY s.currentStatus")
    List<Object[]> countGroupByStatus();
}
