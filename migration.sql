-- Create managed_services table
CREATE TABLE IF NOT EXISTS managed_services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ip_address VARCHAR(15) NOT NULL,
    port INTEGER NOT NULL,
    description TEXT,
    current_status VARCHAR(20) DEFAULT 'UNKNOWN',
    last_check_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create health_checks_history table
CREATE TABLE IF NOT EXISTS health_checks_history (
    id BIGSERIAL PRIMARY KEY,
    service_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    response_time_ms INTEGER,
    error_message TEXT,
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES managed_services(id) ON DELETE CASCADE
);

-- Create index untuk performa query
CREATE INDEX IF NOT EXISTS idx_health_checks_service_id ON health_checks_history(service_id);
CREATE INDEX IF NOT EXISTS idx_health_checks_checked_at ON health_checks_history(checked_at);
CREATE INDEX IF NOT EXISTS idx_managed_services_status ON managed_services(current_status);
