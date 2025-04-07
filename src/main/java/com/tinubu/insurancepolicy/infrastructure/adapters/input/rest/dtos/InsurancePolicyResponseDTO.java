package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos;

public record InsurancePolicyResponseDTO(Integer id,
                                         String name,
                                         String status,
                                         String coverageStartDate,
                                         String coverageEndDate,
                                         String createdAt,
                                         String updatedAt) {
}
