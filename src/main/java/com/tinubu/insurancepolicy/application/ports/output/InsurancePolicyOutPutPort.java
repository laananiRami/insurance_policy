package com.tinubu.insurancepolicy.application.ports.output;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;

import java.util.List;

public interface InsurancePolicyOutPutPort {

    /**
     * Recovers all insurance policies
     *
     * @return List of insurance policies
     */
    List<InsurancePolicy> findAll();

}
