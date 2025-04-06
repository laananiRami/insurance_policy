package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InsurancePolicyResponseDTO(Integer id,
                                         String name,
                                         String status,
                                         LocalDate coverageStartDate,
                                         LocalDate coverageEndDate,
                                         LocalDateTime createdAt,
                                         LocalDateTime updatedAt) {
}
