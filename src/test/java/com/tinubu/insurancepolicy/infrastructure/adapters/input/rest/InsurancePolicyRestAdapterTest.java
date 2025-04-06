package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest;

import com.tinubu.insurancepolicy.application.ports.input.usecases.GetAllInsurancePoliciesUseCase;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class InsurancePolicyRestAdapterTest {

    @Mock
    private GetAllInsurancePoliciesUseCase getAllInsurancePoliciesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnListOfPolicies() {
        // Arrange
        InsurancePolicy policy1 = new InsurancePolicy();
        InsurancePolicy policy2 = new InsurancePolicy();
        List<InsurancePolicy> expectedPolicies = Arrays.asList(policy1, policy2);

        when(getAllInsurancePoliciesUseCase.execute()).thenReturn(expectedPolicies);

        // Act
        List<InsurancePolicy> actualPolicies = getAllInsurancePoliciesUseCase.execute();

        // Assert
        assertNotNull(actualPolicies);
        assertEquals(2, actualPolicies.size());
        assertEquals(expectedPolicies, actualPolicies);
    }


}