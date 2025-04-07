package com.tinubu.insurancepolicy.domain.service;

import com.tinubu.insurancepolicy.application.ports.input.usecases.UpdateInsurancePolicyUseCase;
import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateInsurancePolicyUseCaseImpl implements UpdateInsurancePolicyUseCase {

    private final InsurancePolicyOutPutPort policyRepository;
    private final GetInsurancePolicyByIdUseCaseImpl getInsurancePolicyByIdUseCase;

    public UpdateInsurancePolicyUseCaseImpl(InsurancePolicyOutPutPort policyRepository, GetInsurancePolicyByIdUseCaseImpl getInsurancePolicyByIdUseCase) {
        this.policyRepository = policyRepository;
        this.getInsurancePolicyByIdUseCase = getInsurancePolicyByIdUseCase;
    }

    @Override
    @Transactional
    public InsurancePolicy execute(Integer id, @NotNull InsurancePolicy policy) {
        InsurancePolicy existingPolicy = getInsurancePolicyByIdUseCase.execute(id);

        existingPolicy.setName(policy.getName());
        existingPolicy.setStatus(policy.getStatus());
        existingPolicy.setCoverageStartDate(policy.getCoverageStartDate());
        existingPolicy.setCoverageEndDate(policy.getCoverageEndDate());
        existingPolicy.setUpdatedAt(LocalDateTime.now());

        return policyRepository.save(existingPolicy);
    }
}
