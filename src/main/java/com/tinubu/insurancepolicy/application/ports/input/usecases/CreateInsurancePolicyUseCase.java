package com.tinubu.insurancepolicy.application.ports.input.usecases;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;

public interface CreateInsurancePolicyUseCase {

    /**
     * Created a new insurance policy
     *
     * @param policy insurance policy to create
     * @return insurance policy created
     */
    InsurancePolicy execute(InsurancePolicy policy);

}
