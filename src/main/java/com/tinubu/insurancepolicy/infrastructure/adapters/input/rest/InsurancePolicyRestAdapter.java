package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest;

import com.tinubu.insurancepolicy.application.ports.input.usecases.CreateInsurancePolicyUseCase;
import com.tinubu.insurancepolicy.application.ports.input.usecases.GetAllInsurancePoliciesUseCase;
import com.tinubu.insurancepolicy.application.ports.input.usecases.GetInsurancePolicyByIdUseCase;
import com.tinubu.insurancepolicy.application.ports.input.usecases.UpdateInsurancePolicyUseCase;
import com.tinubu.insurancepolicy.domain.exception.PolicyNotFoundException;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyRequestDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyResponseDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.mapper.InsurancePolicyDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config.ApiPathsConfig.POLICIES_BASE_PATH;

@RestController
@RequestMapping(POLICIES_BASE_PATH)
public class InsurancePolicyRestAdapter {

    private final GetInsurancePolicyByIdUseCase getInsurancePolicyByIdUseCase;
    private final GetAllInsurancePoliciesUseCase getAllInsurancePoliciesUseCase;
    private final CreateInsurancePolicyUseCase createInsurancePolicyUseCase;
    private final UpdateInsurancePolicyUseCase updateInsurancePolicyUseCase;
    private final InsurancePolicyDtoMapper insurancePolicyDtoMapper;

    public InsurancePolicyRestAdapter(GetInsurancePolicyByIdUseCase getInsurancePolicyByIdUseCase, GetAllInsurancePoliciesUseCase getAllInsurancePoliciesUseCase, CreateInsurancePolicyUseCase createInsurancePolicyUseCase, UpdateInsurancePolicyUseCase updateInsurancePolicyUseCase, InsurancePolicyDtoMapper insurancePolicyDtoMapper) {
        this.getInsurancePolicyByIdUseCase = getInsurancePolicyByIdUseCase;
        this.getAllInsurancePoliciesUseCase = getAllInsurancePoliciesUseCase;
        this.createInsurancePolicyUseCase = createInsurancePolicyUseCase;
        this.updateInsurancePolicyUseCase = updateInsurancePolicyUseCase;
        this.insurancePolicyDtoMapper = insurancePolicyDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<InsurancePolicyResponseDTO>> getAllPolicies() {
        List<InsurancePolicyResponseDTO> policies = getAllInsurancePoliciesUseCase.execute().stream()
                .map(insurancePolicyDtoMapper::toResponseDto)
                .toList();

        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsurancePolicyResponseDTO> getPolicyById(@PathVariable Integer id) throws PolicyNotFoundException {
        InsurancePolicyResponseDTO policy = insurancePolicyDtoMapper.toResponseDto(
                getInsurancePolicyByIdUseCase.execute(id)
        );
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InsurancePolicyResponseDTO> createPolicy(@Valid @RequestBody InsurancePolicyRequestDTO requestDTO) {
        InsurancePolicy insurancePolicy = insurancePolicyDtoMapper.toInsurancePolicy(requestDTO);
        InsurancePolicy savedInsurancePolicy = createInsurancePolicyUseCase.execute(insurancePolicy);
        return new ResponseEntity<>(insurancePolicyDtoMapper.toResponseDto(savedInsurancePolicy), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsurancePolicyResponseDTO> updatePolicy(
            @PathVariable Integer id,
            @Valid @RequestBody InsurancePolicyRequestDTO requestDTO) {
        InsurancePolicy insurancePolicy = insurancePolicyDtoMapper.toInsurancePolicy(requestDTO);
        InsurancePolicy updatedPolicy = updateInsurancePolicyUseCase.execute(id, insurancePolicy);
        return ResponseEntity.ok(insurancePolicyDtoMapper.toResponseDto(updatedPolicy));
    }

}
