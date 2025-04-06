package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.input.usecases.GetAllInsurancePoliciesUseCase;
import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllInsurancePoliciesUseCaseImplTest {

    @Mock
    private InsurancePolicyOutPutPort policyRepository;

    private GetAllInsurancePoliciesUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetAllInsurancePoliciesUseCaseImpl(policyRepository);
    }

    @Test
    void shouldReturnAllPoliciesFromRepository() {
        InsurancePolicy policy1 = new InsurancePolicy();
        InsurancePolicy policy2 = new InsurancePolicy();
        List<InsurancePolicy> expectedPolicies = Arrays.asList(policy1, policy2);

        when(policyRepository.findAll()).thenReturn(expectedPolicies);

        List<InsurancePolicy> actualPolicies = useCase.execute();

        assertNotNull(actualPolicies);
        assertEquals(expectedPolicies.size(), actualPolicies.size());
        assertEquals(expectedPolicies, actualPolicies);
        verify(policyRepository).findAll();
    }

}