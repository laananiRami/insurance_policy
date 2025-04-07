package com.tinubu.insurancepolicy.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tinubu.insurancepolicy.application.ports.input.usecases.CreateInsurancePolicyUseCase;
import com.tinubu.insurancepolicy.application.ports.input.usecases.GetAllInsurancePoliciesUseCase;
import com.tinubu.insurancepolicy.application.ports.input.usecases.GetInsurancePolicyByIdUseCase;
import com.tinubu.insurancepolicy.application.ports.input.usecases.UpdateInsurancePolicyUseCase;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyRequestDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyResponseDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.mapper.InsurancePolicyDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config.ApiPathsConfig.POLICIES_BASE_PATH;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InsurancePolicyRestAdapterTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private GetAllInsurancePoliciesUseCase getAllInsurancePoliciesUseCase;

    @Mock
    private GetInsurancePolicyByIdUseCase getInsurancePolicyByIdUseCase;

    @Mock
    private CreateInsurancePolicyUseCase createInsurancePolicyUseCase;

    @Mock
    private UpdateInsurancePolicyUseCase updateInsurancePolicyUseCase;

    @Mock
    private InsurancePolicyDtoMapper insurancePolicyDtoMapper;

    LocalDate startDate;
    LocalDate endDate;
    LocalDateTime now;
    String startDateString;
    String endDateString;
    String nowString;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        InsurancePolicyRestAdapter restAdapter = new InsurancePolicyRestAdapter(
                getInsurancePolicyByIdUseCase,
                getAllInsurancePoliciesUseCase,
                createInsurancePolicyUseCase,
                updateInsurancePolicyUseCase,
                insurancePolicyDtoMapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(restAdapter).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2024, 1, 1);
        now = LocalDateTime.now();
        startDateString = startDate.toString();
        endDateString = endDate.toString();
        nowString = now.toString();
    }

    @Test
    void getAllPolicies_ShouldReturnListOfPolicies() throws Exception {
        // Given
        InsurancePolicy policy1 = InsurancePolicy.builder()
                .id(1)
                .name("Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        InsurancePolicy policy2 = InsurancePolicy.builder()
                .id(2)
                .name("Health Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<InsurancePolicy> policies = Arrays.asList(policy1, policy2);

        InsurancePolicyResponseDTO dto1 = new InsurancePolicyResponseDTO(
                1, "Life Insurance", "ACTIVE", startDateString, endDateString, nowString, nowString);

        InsurancePolicyResponseDTO dto2 = new InsurancePolicyResponseDTO(
                2, "Health Insurance", "PENDING", startDateString, endDateString, nowString, nowString);

        when(getAllInsurancePoliciesUseCase.execute()).thenReturn(policies);
        when(insurancePolicyDtoMapper.toResponseDto(policy1)).thenReturn(dto1);
        when(insurancePolicyDtoMapper.toResponseDto(policy2)).thenReturn(dto2);

        // When && Then
        mockMvc.perform(get(POLICIES_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Life Insurance")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Health Insurance")));
    }

    @Test
    void test_getPolicyById_ShouldReturnPolicy_WhenPolicyExists() throws Exception {
        // Given
        Integer policyId = 1;

        InsurancePolicy policy = InsurancePolicy.builder()
                .id(policyId)
                .name("Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        InsurancePolicyResponseDTO dto = new InsurancePolicyResponseDTO(
                policyId, "Life Insurance", "ACTIVE", startDateString, endDateString, nowString, nowString);

        when(getInsurancePolicyByIdUseCase.execute(policyId)).thenReturn(policy);
        when(insurancePolicyDtoMapper.toResponseDto(policy)).thenReturn(dto);

        // When && Then
        mockMvc.perform(MockMvcRequestBuilders.get(POLICIES_BASE_PATH + "/{id}", policyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(policyId)))
                .andExpect(jsonPath("$.name", is("Life Insurance")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }

    @Test
    void test_createPolicy_ShouldCreateAndReturnPolicy() throws Exception {
        // Given
        InsurancePolicyRequestDTO requestDTO = new InsurancePolicyRequestDTO(
                "Life Insurance", "ACTIVE", startDateString, endDateString);

        InsurancePolicy newPolicy = InsurancePolicy.builder()
                .name("Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .build();

        InsurancePolicy savedPolicy = InsurancePolicy.builder()
                .id(1)
                .name("Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        InsurancePolicyResponseDTO responseDTO = new InsurancePolicyResponseDTO(
                1, "Life Insurance", "ACTIVE", startDateString, endDateString, nowString, nowString);

        when(insurancePolicyDtoMapper.toInsurancePolicy(requestDTO)).thenReturn(newPolicy);
        when(createInsurancePolicyUseCase.execute(newPolicy)).thenReturn(savedPolicy);
        when(insurancePolicyDtoMapper.toResponseDto(savedPolicy)).thenReturn(responseDTO);

        // When && Then
        mockMvc.perform(post(POLICIES_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Life Insurance")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }

    @Test
    void test_updatePolicy_ShouldUpdateAndReturnPolicy() throws Exception {
        // When
        Integer policyId = 1;

        InsurancePolicyRequestDTO requestDTO = new InsurancePolicyRequestDTO(
                "Updated Life Insurance", "ACTIVE", startDateString, endDateString);

        InsurancePolicy policyToUpdate = InsurancePolicy.builder()
                .name("Updated Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .build();

        InsurancePolicy updatedPolicy = InsurancePolicy.builder()
                .id(policyId)
                .name("Updated Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate)
                .coverageEndDate(endDate)
                .createdAt(now)
                .updatedAt(now)
                .build();

        InsurancePolicyResponseDTO responseDTO = new InsurancePolicyResponseDTO(
                policyId, "Updated Life Insurance", "ACTIVE", startDateString, endDateString, nowString, nowString);

        when(insurancePolicyDtoMapper.toInsurancePolicy(requestDTO)).thenReturn(policyToUpdate);
        when(updateInsurancePolicyUseCase.execute(eq(policyId), any(InsurancePolicy.class))).thenReturn(updatedPolicy);
        when(insurancePolicyDtoMapper.toResponseDto(updatedPolicy)).thenReturn(responseDTO);

        // When && Then
        mockMvc.perform(put(POLICIES_BASE_PATH + "/{id}", policyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(policyId)))
                .andExpect(jsonPath("$.name", is("Updated Life Insurance")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }

}