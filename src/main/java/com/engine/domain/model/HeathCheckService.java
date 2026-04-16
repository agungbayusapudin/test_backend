package com.engine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_check_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeathCheckHisotry {
    private Long id;
    private Long service_id;
    private String status;
    private String status;
    private Integer response_time;
    private String error_message;
    private LocalDateTime checkedAt;
}