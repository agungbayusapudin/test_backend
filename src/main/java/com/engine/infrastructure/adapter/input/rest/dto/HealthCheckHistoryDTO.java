package com.engine.infrastructure.adapter.input.rest.dto;

import com.engine.domain.model.HealthCheckHistory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class HealthCheckHistoryDTO {

    @Data
    @Builder
    public static class Response {
        private Long id;
        private String timestamp;
        private String status;
        private Long serviceId;
        private Integer responseTimeMs;
        private String errorMessage;

        public static Response fromDomain(HealthCheckHistory history) {
            return Response.builder()
                    .id(history.getId())
                    .timestamp(history.getCheckedAt().toString())
                    .status(history.getStatus())
                    .serviceId(history.getService().getId())
                    .responseTimeMs(history.getResponseTimeMs())
                    .errorMessage(history.getErrorMessage())
                    .build();
        }
    }
}