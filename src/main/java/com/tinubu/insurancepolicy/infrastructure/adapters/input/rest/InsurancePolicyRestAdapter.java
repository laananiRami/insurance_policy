package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest;

import com.tinubu.insurancepolicy.application.ports.input.usecases.GetAllInsurancePoliciesUseCase;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyResponseDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.mapper.InsurancePolicyDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config.ApiPathsConfig.POLICIES_BASE_PATH;

@RestController
@RequestMapping(POLICIES_BASE_PATH)
public class InsurancePolicyRestAdapter {

    private final GetAllInsurancePoliciesUseCase getAllInsurancePoliciesUseCase;
    private final InsurancePolicyDtoMapper insurancePolicyDtoMapper;

    public InsurancePolicyRestAdapter(GetAllInsurancePoliciesUseCase getAllInsurancePoliciesUseCase, InsurancePolicyDtoMapper insurancePolicyDtoMapper) {
        this.getAllInsurancePoliciesUseCase = getAllInsurancePoliciesUseCase;
        this.insurancePolicyDtoMapper = insurancePolicyDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<InsurancePolicyResponseDTO>> getAllPolicies() {
        List<InsurancePolicyResponseDTO> policies = getAllInsurancePoliciesUseCase.execute().stream()
                .map(insurancePolicyDtoMapper::toResponseDto)
                .toList();

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

}
