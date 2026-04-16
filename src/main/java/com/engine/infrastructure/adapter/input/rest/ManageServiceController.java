package com.engine.infrastructure.adapter.input.rest;

import com.engine.aplication.port.input.ManageServiceUseCase;
import com.engine.infrastructure.adapter.input.rest.dto.ManageServiceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Managed Services", description = "CRUD operations for managing monitored services")
public class ManageServiceController {

    private final ManageServiceUseCase serviceUseCase;

    @Operation(summary = "Get all services", description = "Retrieve list of all registered services")
    @ApiResponse(responseCode = "200", description = "List of services retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ManageServiceDTO.Response>> getAllServices() {
        return ResponseEntity.ok(
                serviceUseCase.getAllServices().stream()
                        .map(ManageServiceDTO.Response::fromDomain)
                        .collect(Collectors.toList())
        );
    }

    @Operation(summary = "Get service by ID", description = "Retrieve a single service by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service found"),
            @ApiResponse(responseCode = "404", description = "Service not found", content = @Content(schema = @Schema(example = "{\"status\":404,\"message\":\"Service not found with id: 1\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ManageServiceDTO.Response> getServiceById(
            @Parameter(description = "Service ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(ManageServiceDTO.Response.fromDomain(serviceUseCase.getServiceById(id)));
    }

    @Operation(summary = "Create new service", description = "Register a new service to be monitored. Requires X-API-KEY header.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Service created successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid API key"),
            @ApiResponse(responseCode = "409", description = "Service with same name or IP:port already exists")
    })
    @SecurityRequirement(name = "X-API-KEY")
    @PostMapping
    public ResponseEntity<ManageServiceDTO.Response> createService(@RequestBody ManageServiceDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ManageServiceDTO.Response.fromDomain(serviceUseCase.createService(request.toDomain())));
    }

    @Operation(summary = "Update service", description = "Update an existing service by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service updated successfully"),
            @ApiResponse(responseCode = "404", description = "Service not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ManageServiceDTO.Response> updateService(
            @Parameter(description = "Service ID", required = true) @PathVariable Long id,
            @RequestBody ManageServiceDTO.Request request) {
        return ResponseEntity.ok(ManageServiceDTO.Response.fromDomain(serviceUseCase.updateService(id, request.toDomain())));
    }

    @Operation(summary = "Delete service", description = "Delete a service by its ID. Requires X-API-KEY header.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Service deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid API key"),
            @ApiResponse(responseCode = "404", description = "Service not found")
    })
    @SecurityRequirement(name = "X-API-KEY")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @Parameter(description = "Service ID", required = true) @PathVariable Long id) {
        serviceUseCase.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
