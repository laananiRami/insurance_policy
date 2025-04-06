package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.input.usecases.GetAllInsurancePoliciesUseCase;
import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllInsurancePoliciesUseCaseImpl implements GetAllInsurancePoliciesUseCase {

    private final InsurancePolicyOutPutPort policyRepository;

    public GetAllInsurancePoliciesUseCaseImpl(InsurancePolicyOutPutPort policyRepository) {
        this.policyRepository = policyRepository;
    }


    @Override
    public List<InsurancePolicy> execute() {
        return policyRepository.findAll();
    }
}
