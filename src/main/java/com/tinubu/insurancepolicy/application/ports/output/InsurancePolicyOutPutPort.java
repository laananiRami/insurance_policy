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

    /**
     * Recovers an insurance policy by their identifier
     *
     * @param id Police identifier
     * @return Insurance policy found
     */
    InsurancePolicy findById(Integer id);

    /**
     * Save or update an insurance policy
     *
     * @param policy Insurance Police to be created
     * @return Insurance policy created
     */
    InsurancePolicy save(InsurancePolicy policy);

}
