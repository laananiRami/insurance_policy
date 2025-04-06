package com.tinubu.insurancepolicy.application.ports.input.usecases;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;

import java.util.List;

public interface GetAllInsurancePoliciesUseCase {

    /**
     * Recovers all insurance policies
     *
     * @return list of insurance policies
     */
    List<InsurancePolicy> execute();

}
