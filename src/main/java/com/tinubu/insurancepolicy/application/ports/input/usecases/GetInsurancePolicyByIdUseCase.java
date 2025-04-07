package com.tinubu.insurancepolicy.application.ports.input.usecases;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;

public interface GetInsurancePolicyByIdUseCase {

    /**
     * Recovers an insurance policy by his identifier
     *
     * @param id insurance policy identifier
     * @return insurance policy found
     */
    InsurancePolicy execute(Integer id);
}
