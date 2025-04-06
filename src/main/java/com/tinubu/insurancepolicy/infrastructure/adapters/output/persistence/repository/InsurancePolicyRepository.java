package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.repository;

import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicyEntity, Integer> {
}
