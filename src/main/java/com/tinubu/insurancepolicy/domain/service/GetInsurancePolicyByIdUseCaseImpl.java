package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.input.usecases.GetInsurancePolicyByIdUseCase;
import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import org.springframework.stereotype.Service;

@Service
public class GetInsurancePolicyByIdUseCaseImpl implements GetInsurancePolicyByIdUseCase {

    private final InsurancePolicyOutPutPort policyRepository;

    public GetInsurancePolicyByIdUseCaseImpl(InsurancePolicyOutPutPort policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public InsurancePolicy execute(Integer id) {
        return policyRepository.findById(id);
    }
}
