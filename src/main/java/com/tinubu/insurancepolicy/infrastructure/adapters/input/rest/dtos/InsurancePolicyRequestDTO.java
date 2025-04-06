package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InsurancePolicyRequestDTO(
        @NotBlank(message = "The police name cannot be empty")
        String name,

        @NotNull(message = "Police status is compulsory")
        String status,

        @NotNull(message = "The starting date of coverage is compulsory")
        LocalDate coverageStartDate,

        @NotNull(message = "The end date of coverage is compulsory")
        LocalDate coverageEndDate) {

}
