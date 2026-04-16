package com.engine.infrastructure.adapter.input.rest;

import com.engine.aplication.port.input.HealthCheckUseCase;
import com.engine.infrastructure.adapter.input.rest.dto.HealthCheckHistoryDTO;
import com.engine.infrastructure.adapter.input.rest.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Health Check History", description = "Health check history for monitored services")
public class HealthCheckHistoryController {

    private final HealthCheckUseCase healthCheckUseCase;

    @Operation(summary = "Get health check history", description = "Retrieve paginated health check history for a specific service")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "History retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Service not found")
    })
    @GetMapping("/{id}/history")
    public ResponseEntity<PagedResponse<HealthCheckHistoryDTO.Response>> getHealthCheckHistory(
            @Parameter(description = "Service ID", required = true) @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "checkedAt"));
        return ResponseEntity.ok(
                PagedResponse.of(healthCheckUseCase.getHealthCheckHistory(id, pageable),
                        HealthCheckHistoryDTO.Response::fromDomain)
        );
    }
}
