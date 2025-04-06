package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.mapper;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyEntity;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyStatusEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InsurancePolicyMapperTest {

    @Test
    void shouldMapEntityToDomain() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        InsurancePolicyEntity entity = InsurancePolicyEntity.builder()
                .id(1)
                .name("Test Policy")
                .status(InsurancePolicyStatusEntity.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // When
        InsurancePolicy domain = InsurancePolicyMapper.INSTANCE.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(InsurancePolicyStatus.ACTIVE, domain.getStatus());
        assertEquals(entity.getCoverageStartDate(), domain.getCoverageStartDate());
        assertEquals(entity.getCoverageEndDate(), domain.getCoverageEndDate());
        assertEquals(entity.getCreatedAt(), domain.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), domain.getUpdatedAt());
    }

    @Test
    void shouldMapDomainToEntity() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        InsurancePolicy domain = InsurancePolicy.builder()
                .id(1)
                .name("Test Policy")
                .status(InsurancePolicyStatus.INACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // When
        InsurancePolicyEntity entity = InsurancePolicyMapper.INSTANCE.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(InsurancePolicyStatusEntity.INACTIVE, entity.getStatus());
        assertEquals(domain.getCoverageStartDate(), entity.getCoverageStartDate());
        assertEquals(domain.getCoverageEndDate(), entity.getCoverageEndDate());
        assertEquals(domain.getCreatedAt(), entity.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), entity.getUpdatedAt());
    }


}