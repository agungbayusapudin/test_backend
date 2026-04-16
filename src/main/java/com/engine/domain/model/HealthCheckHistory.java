package com.engine.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_checks_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthCheckHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ManagedService service;

    @Column(nullable = false)
    private String status;

    @Column(name = "response_time_ms")
    private Integer responseTimeMs;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @PrePersist
    protected void onCreate() {
        checkedAt = LocalDateTime.now();
    }
}
