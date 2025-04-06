package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.mapper;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyRequestDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface InsurancePolicyDtoMapper {

    @Mapping(target = "status", expression = "java(mapStatus(insurancePolicyRequestDTO.status()))")
    InsurancePolicy toInsurancePolicy(InsurancePolicyRequestDTO insurancePolicyRequestDTO);

    InsurancePolicyResponseDTO toResponseDto(InsurancePolicy insurancePolicy);

    default InsurancePolicyStatus mapStatus(String status) {
        if (status == null) {
            return null;
        }

        return InsurancePolicyStatus.valueOf(status);
    }
}
