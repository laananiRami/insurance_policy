package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence;

import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicyStatus;
import com.tinubu.insurancepolicy.domain.service.GetAllInsurancePoliciesUseCaseImpl;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.InsurancePolicyRestAdapter;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.dtos.InsurancePolicyResponseDTO;
import com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.mapper.InsurancePolicyDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tinubu.insurancepolicy.infrastructure.adapters.input.rest.config.ApiPathsConfig.POLICIES_BASE_PATH;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class InsurancePolicyPersistenceAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private InsurancePolicyPersistenceAdapter persistenceAdapter;

    @Mock
    private InsurancePolicyDtoMapper dtoMapper;

    private InsurancePolicyRestAdapter restAdapter;

    @BeforeEach
    void setUp() {
        GetAllInsurancePoliciesUseCaseImpl getAllPoliciesUseCase =
                new GetAllInsurancePoliciesUseCaseImpl(persistenceAdapter);

        restAdapter = new InsurancePolicyRestAdapter(getAllPoliciesUseCase, dtoMapper);

        // Set up MockMvc
        mockMvc = MockMvcBuilders
                .standaloneSetup(restAdapter)
                .build();
    }

    @Test
    void getAllPolicies_ShouldReturnPoliciesList() throws Exception {
        // Given
        LocalDate startDate1 = LocalDate.of(2023, 1, 1);
        LocalDate endDate1 = LocalDate.of(2024, 1, 1);
        LocalDateTime now = LocalDateTime.now();

        InsurancePolicy policy1 = InsurancePolicy.builder()
                .id(1)
                .name("Life Insurance")
                .status(InsurancePolicyStatus.ACTIVE)
                .coverageStartDate(startDate1)
                .coverageEndDate(endDate1)
                .createdAt(now)
                .updatedAt(now)
                .build();

        InsurancePolicy policy2 = InsurancePolicy.builder()
                .id(2)
                .name("Health Insurance")
                .status(InsurancePolicyStatus.INACTIVE)
                .coverageStartDate(startDate1.plusMonths(1))
                .coverageEndDate(endDate1.plusMonths(1))
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<InsurancePolicy> policies = Arrays.asList(policy1, policy2);

        InsurancePolicyResponseDTO responseDTO1 = new InsurancePolicyResponseDTO(
                1,
                "Life Insurance",
                "ACTIVE",
                startDate1,
                endDate1,
                now,
                now
        );

        InsurancePolicyResponseDTO responseDTO2 = new InsurancePolicyResponseDTO(
                2,
                "Health Insurance",
                "PENDING",
                startDate1.plusMonths(1),
                endDate1.plusMonths(1),
                now,
                now
        );

        when(persistenceAdapter.findAll()).thenReturn(policies);
        when(dtoMapper.toResponseDto(policy1)).thenReturn(responseDTO1);
        when(dtoMapper.toResponseDto(policy2)).thenReturn(responseDTO2);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(POLICIES_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Life Insurance")))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Health Insurance")))
                .andExpect(jsonPath("$[1].status", is("PENDING")));

        verify(persistenceAdapter, times(1)).findAll();
        verify(dtoMapper, times(1)).toResponseDto(policy1);
        verify(dtoMapper, times(1)).toResponseDto(policy2);
    }

    @Test
    void getAllPolicies_ShouldReturnEmptyList_WhenNoPoliciesExist() throws Exception {
        // Given
        when(persistenceAdapter.findAll()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(POLICIES_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(persistenceAdapter, times(1)).findAll();
        verify(dtoMapper, never()).toResponseDto(any());
    }
}