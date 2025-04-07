package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.mapper;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyRequestDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {InsurancePolicyDtoMapperImpl.class})
class InsurancePolicyDtoMapperTest {

    @Autowired
    private InsurancePolicyDtoMapper mapper;

    LocalDate startDate;
    LocalDate endDate;
    LocalDateTime now;
    String startDateString;
    String endDateString;
    String nowString;

    @BeforeEach
    void setup() {
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2024, 1, 1);
        now = LocalDateTime.now();
        startDateString = startDate.toString();
        endDateString = endDate.toString();
        nowString = now.toString();
    }

    @Test
    void shouldMapRequestDtoToEntity() {
        // Given
        InsurancePolicyRequestDTO requestDTO = new InsurancePolicyRequestDTO(
                "Life Insurance Policy",
                "ACTIVE",
                startDateString,
                endDateString
        );

        // When
        InsurancePolicy result = mapper.toInsurancePolicy(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(requestDTO.name(), result.getName());
        assertEquals(InsurancePolicyStatus.ACTIVE, result.getStatus());
        assertEquals(requestDTO.coverageStartDate(), result.getCoverageStartDate().toString());
        assertEquals(requestDTO.coverageEndDate(), result.getCoverageEndDate().toString());
        // createdAt and updatedAt are not part of mapping from request
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
        assertNull(result.getId());
    }

    @Test
    void shouldMapEntityToResponseDto() {
        // Given
        InsurancePolicy entity = InsurancePolicy.builder()
                .id(1)
                .name("Life Insurance Policy")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // When
        InsurancePolicyResponseDTO result = mapper.toResponseDto(entity);

        // Then
        assertNotNull(result);
        assertEquals(entity.getId(), result.id());
        assertEquals(entity.getName(), result.name());
        assertEquals(entity.getStatus().name(), result.status());
        assertEquals(entity.getCoverageStartDate().toString(), result.coverageStartDate());
        assertEquals(entity.getCoverageEndDate().toString(), result.coverageEndDate());
        assertEquals(entity.getCreatedAt().toString(), result.createdAt());
        assertEquals(entity.getUpdatedAt().toString(), result.updatedAt());
    }

    @Test
    void shouldMapNullStatusToNull() {
        // When
        InsurancePolicyStatus result = mapper.mapStatus(null);

        // Then
        assertNull(result);
    }
}