package com.engine.domain.exception;

public class Exception {

    // Dilempar ketika service tidak ditemukan by ID
    public static class ServiceNotFoundException extends RuntimeException {
        public ServiceNotFoundException(Long id) {
            super("Service not found with id: " + id);
        }
    }

    // Dilempar ketika nama service sudah terdaftar
    public static class ServiceNameAlreadyExistsException extends RuntimeException {
        public ServiceNameAlreadyExistsException(String name) {
            super("Service with name '" + name + "' already exists");
        }
    }

    // Dilempar ketika kombinasi IP + port sudah terdaftar
    public static class ServiceAddressAlreadyExistsException extends RuntimeException {
        public ServiceAddressAlreadyExistsException(String ipAddress, Integer port) {
            super("Service with address " + ipAddress + ":" + port + " already exists");
        }
    }

    // Dilempar ketika request body tidak valid (field kosong, format salah, dll)
    public static class InvalidServiceRequestException extends RuntimeException {
        public InvalidServiceRequestException(String message) {
            super(message);
        }
    }
}
