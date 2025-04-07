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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateInsurancePolicyUseCaseImplTest {

    @Mock
    private InsurancePolicyOutPutPort policyRepository;

    private CreateInsurancePolicyUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateInsurancePolicyUseCaseImpl(policyRepository);
    }

    @Test
    void shouldCreatePolicyWithCurrentTimestamps() {
        // Given
        InsurancePolicy inputPolicy = InsurancePolicy.builder()
                .name("Test Policy")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(LocalDate.now())
                .coverageEndDate(LocalDate.now().plusYears(1))
                .build();

        InsurancePolicy savedPolicy = InsurancePolicy.builder()
                .id(1)
                .name("Test Policy")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(LocalDate.now())
                .coverageEndDate(LocalDate.now().plusYears(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(policyRepository.save(any(InsurancePolicy.class))).thenReturn(savedPolicy);

        // When
        InsurancePolicy result = useCase.execute(inputPolicy);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void shouldDelegateToRepository() {
        // Given
        InsurancePolicy inputPolicy = InsurancePolicy.builder()
                .name("Test Policy")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(LocalDate.now())
                .coverageEndDate(LocalDate.now().plusYears(1))
                .build();

        // When
        useCase.execute(inputPolicy);

        // Then
        verify(policyRepository, times(1)).save(any(InsurancePolicy.class));
    }
}