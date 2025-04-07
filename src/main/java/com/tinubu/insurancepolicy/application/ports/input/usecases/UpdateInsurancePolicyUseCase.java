package com.tinubu.insurancepolicy.application.ports.input.usecases;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;

public interface UpdateInsurancePolicyUseCase {

    /**
     * Update an existing insurance policy @param ID Police identifier to update
     *
     * @param policy update data
     * @RETURN Insurance policy update
     */
    InsurancePolicy execute(Integer id, InsurancePolicy policy);

}
