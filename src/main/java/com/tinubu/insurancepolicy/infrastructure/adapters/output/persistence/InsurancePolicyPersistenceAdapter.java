package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence;

import com.tinubu.insurancepolicy.application.ports.output.InsurancePolicyOutPutPort;
import com.tinubu.insurancepolicy.domain.exception.PolicyNotFoundException;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyEntity;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.mapper.InsurancePolicyMapper;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.repository.InsurancePolicyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InsurancePolicyPersistenceAdapter implements InsurancePolicyOutPutPort {

    private final InsurancePolicyRepository insurancePolicyRepository;
    private final InsurancePolicyMapper insurancePolicyMapper;

    public InsurancePolicyPersistenceAdapter(InsurancePolicyRepository insurancePolicyRepository, InsurancePolicyMapper insurancePolicyMapper) {
        this.insurancePolicyRepository = insurancePolicyRepository;
        this.insurancePolicyMapper = insurancePolicyMapper;
    }

    @Override
    public List<InsurancePolicy> findAll() {
        return insurancePolicyRepository.findAll().stream().map(insurancePolicyMapper::toDomain).toList();
    }

    @Override
    public InsurancePolicy findById(Integer id) {
        InsurancePolicyEntity insurancePolicyEntity = insurancePolicyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException(id));
        return insurancePolicyMapper.toDomain(insurancePolicyEntity);
    }

    @Override
    public InsurancePolicy save(InsurancePolicy policy) {
        InsurancePolicyEntity insurancePolicyEntity = insurancePolicyMapper.toEntity(policy);
        InsurancePolicyEntity savedInsurancePolicyEntity = insurancePolicyRepository.save(insurancePolicyEntity);
        return insurancePolicyMapper.toDomain(savedInsurancePolicyEntity);
    }

}
