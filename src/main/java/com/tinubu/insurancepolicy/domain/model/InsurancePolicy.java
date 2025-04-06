package com.tinubu.insurancepolicy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicy {
    private Integer id;
    private String name;
    private InsurancePolicyStatus status;
    private LocalDate coverageStartDate;
    private LocalDate coverageEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
