package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.input.usecases.CreateInsurancePolicyUseCase;
import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateInsurancePolicyUseCaseImpl implements CreateInsurancePolicyUseCase {

    private final InsurancePolicyOutPutPort policyRepository;

    public CreateInsurancePolicyUseCaseImpl(InsurancePolicyOutPutPort policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    @Transactional
    public InsurancePolicy execute(@NotNull InsurancePolicy insurancePolicy) {
        LocalDateTime now = LocalDateTime.now();
        insurancePolicy.setCreatedAt(now);
        insurancePolicy.setUpdatedAt(now);

        return policyRepository.save(insurancePolicy);
    }

}
