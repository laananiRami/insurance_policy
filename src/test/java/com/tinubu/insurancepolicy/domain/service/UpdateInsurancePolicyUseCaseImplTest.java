package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateInsurancePolicyUseCaseImplTest {

    @Mock
    private InsurancePolicyOutPutPort policyRepository;

    @Mock
    private GetInsurancePolicyByIdUseCaseImpl getInsurancePolicyByIdUseCase;

    private UpdateInsurancePolicyUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateInsurancePolicyUseCaseImpl(policyRepository, getInsurancePolicyByIdUseCase);
    }

    @Test
    void test_execute_ShouldUpdateAndSaveExistingPolicy() {
        // Given
        Integer policyId = 1;

        InsurancePolicy existingPolicy = new InsurancePolicy();
        existingPolicy.setId(policyId);
        existingPolicy.setName("Old Policy Name");
        existingPolicy.setStatus(InsurancePolicyStatus.ACTIVE);
        existingPolicy.setCoverageStartDate(LocalDate.of(2023, 1, 1));
        existingPolicy.setCoverageEndDate(LocalDate.of(2023, 12, 31));
        LocalDateTime originalUpdatedAt = LocalDateTime.of(2023, 1, 1, 12, 0);
        existingPolicy.setUpdatedAt(originalUpdatedAt);

        InsurancePolicy updatedPolicy = new InsurancePolicy();
        updatedPolicy.setName("New Policy Name");
        updatedPolicy.setStatus(InsurancePolicyStatus.INACTIVE);
        updatedPolicy.setCoverageStartDate(LocalDate.of(2024, 1, 1));
        updatedPolicy.setCoverageEndDate(LocalDate.of(2024, 12, 31));

        InsurancePolicy savedPolicy = new InsurancePolicy();
        savedPolicy.setId(policyId);
        savedPolicy.setName(updatedPolicy.getName());
        savedPolicy.setStatus(updatedPolicy.getStatus());
        savedPolicy.setCoverageStartDate(updatedPolicy.getCoverageStartDate());
        savedPolicy.setCoverageEndDate(updatedPolicy.getCoverageEndDate());

        when(getInsurancePolicyByIdUseCase.execute(policyId)).thenReturn(existingPolicy);
        when(policyRepository.save(any(InsurancePolicy.class))).thenReturn(savedPolicy);

        // When
        InsurancePolicy result = useCase.execute(policyId, updatedPolicy);

        // Then
        assertNotNull(result);
        assertEquals(policyId, result.getId());
        assertEquals(updatedPolicy.getName(), result.getName());
        assertEquals(updatedPolicy.getStatus(), result.getStatus());
        assertEquals(updatedPolicy.getCoverageStartDate(), result.getCoverageStartDate());
        assertEquals(updatedPolicy.getCoverageEndDate(), result.getCoverageEndDate());

        verify(getInsurancePolicyByIdUseCase).execute(policyId);
    }
}