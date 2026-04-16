package com.engine.infrastructure.adapter.input.rest.dto;

import com.engine.domain.model.ManagedService;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class ManageServiceDTO {

    @Data
    @Builder
    public static class Request {
        private String name;
        private String ipAddress;
        private Integer port;
        private String description;

        public ManagedService toDomain() {
            return ManagedService.builder()
                    .name(this.name)
                    .ipAddress(this.ipAddress)
                    .port(this.port)
                    .description(this.description)
                    .build();
        }
    }

    @Data
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String ipAddress;
        private Integer port;
        private String description;
        private String currentStatus;
        private LocalDateTime lastCheckAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response fromDomain(ManagedService service) {
            return Response.builder()
                    .id(service.getId())
                    .name(service.getName())
                    .ipAddress(service.getIpAddress())
                    .port(service.getPort())
                    .description(service.getDescription())
                    .currentStatus(service.getCurrentStatus())
                    .lastCheckAt(service.getLastCheckAt())
                    .createdAt(service.getCreatedAt())
                    .updatedAt(service.getUpdatedAt())
                    .build();
        }
    }
}
