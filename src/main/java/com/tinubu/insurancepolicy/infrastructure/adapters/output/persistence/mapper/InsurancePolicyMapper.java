package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.mapper;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyEntity;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InsurancePolicyMapper {

    InsurancePolicyMapper INSTANCE = Mappers.getMapper(InsurancePolicyMapper.class);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapEntityStatusToPolicy")
    InsurancePolicy toDomain(InsurancePolicyEntity entity);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapPolicyStatusToEntity")
    InsurancePolicyEntity toEntity(InsurancePolicy domain);

    @Named("mapPolicyStatusToEntity")
    default InsurancePolicyStatusEntity mapPolicyStatusToEntity(InsurancePolicyStatus status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case ACTIVE -> InsurancePolicyStatusEntity.ACTIVE;
            case INACTIVE -> InsurancePolicyStatusEntity.INACTIVE;
        };
    }

    @Named("mapEntityStatusToPolicy")
    default InsurancePolicyStatus mapEntityStatusToPolicy(InsurancePolicyStatusEntity statusEntity) {
        if (statusEntity == null) {
            return null;
        }

        return switch (statusEntity) {
            case ACTIVE -> InsurancePolicyStatus.ACTIVE;
            case INACTIVE -> InsurancePolicyStatus.INACTIVE;
        };
    }
}
