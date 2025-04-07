package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetInsurancePolicyByIdUseCaseImplTest {

    @Mock
    private InsurancePolicyOutPutPort policyRepository;

    private GetInsurancePolicyByIdUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetInsurancePolicyByIdUseCaseImpl(policyRepository);
    }

    @Test
    void test_execute_ShouldReturnPolicyFromRepository() {
        // Arrange
        Integer policyId = 1;
        InsurancePolicy expectedPolicy = new InsurancePolicy();
        expectedPolicy.setId(policyId);

        when(policyRepository.findById(policyId)).thenReturn(expectedPolicy);

        // Act
        InsurancePolicy result = useCase.execute(policyId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPolicy, result);
        assertEquals(policyId, result.getId());
        verify(policyRepository).findById(policyId);
    }
}