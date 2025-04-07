package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence;

import com.tinubu.insurancepolicy.domain.exception.PolicyNotFoundException;
import com.tinubu.insurancepolicy.domain.model.InsurancePolicy;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity.InsurancePolicyEntity;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.mapper.InsurancePolicyMapper;
import com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.repository.InsurancePolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsurancePolicyPersistenceAdapterTest {

    @Mock
    private InsurancePolicyRepository insurancePolicyRepository;

    @Mock
    private InsurancePolicyMapper insurancePolicyMapper;

    private InsurancePolicyPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new InsurancePolicyPersistenceAdapter(insurancePolicyRepository, insurancePolicyMapper);
    }

    @Test
    void test_findAll_return_all_policies() {
        // Given
        InsurancePolicyEntity entity1 = new InsurancePolicyEntity();
        InsurancePolicyEntity entity2 = new InsurancePolicyEntity();
        List<InsurancePolicyEntity> entities = Arrays.asList(entity1, entity2);

        InsurancePolicy policy1 = new InsurancePolicy();
        InsurancePolicy policy2 = new InsurancePolicy();

        when(insurancePolicyRepository.findAll()).thenReturn(entities);
        when(insurancePolicyMapper.toDomain(entity1)).thenReturn(policy1);
        when(insurancePolicyMapper.toDomain(entity2)).thenReturn(policy2);

        // When
        List<InsurancePolicy> result = adapter.findAll();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(policy1));
        assertTrue(result.contains(policy2));
        verify(insurancePolicyRepository).findAll();
        verify(insurancePolicyMapper, times(2)).toDomain(any(InsurancePolicyEntity.class));
    }

    @Test
    void test_findById_WithExistingId() {
        // Given
        Integer id = 1;
        InsurancePolicyEntity entity = new InsurancePolicyEntity();
        InsurancePolicy expectedPolicy = new InsurancePolicy();

        when(insurancePolicyRepository.findById(id)).thenReturn(Optional.of(entity));
        when(insurancePolicyMapper.toDomain(entity)).thenReturn(expectedPolicy);

        // When
        InsurancePolicy result = adapter.findById(id);

        // Then
        assertNotNull(result);
        assertEquals(expectedPolicy, result);
        verify(insurancePolicyRepository).findById(id);
        verify(insurancePolicyMapper).toDomain(entity);
    }

    @Test
    void test_findById_WithNonExistingId_throwException() {
        // Arrange
        Integer id = 999;
        when(insurancePolicyRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PolicyNotFoundException.class,
                () -> adapter.findById(id));

        verify(insurancePolicyRepository).findById(id);
        verify(insurancePolicyMapper, never()).toDomain(any());
    }

    @Test
    void test_save_returnSavedPolicy() {
        // Arrange
        InsurancePolicy policyToSave = new InsurancePolicy();
        InsurancePolicyEntity entityToSave = new InsurancePolicyEntity();
        InsurancePolicyEntity savedEntity = new InsurancePolicyEntity();
        InsurancePolicy expectedSavedPolicy = new InsurancePolicy();

        when(insurancePolicyMapper.toEntity(policyToSave)).thenReturn(entityToSave);
        when(insurancePolicyRepository.save(entityToSave)).thenReturn(savedEntity);
        when(insurancePolicyMapper.toDomain(savedEntity)).thenReturn(expectedSavedPolicy);

        // Act
        InsurancePolicy result = adapter.save(policyToSave);

        // Assert
        assertNotNull(result);
        assertEquals(expectedSavedPolicy, result);
        verify(insurancePolicyMapper).toEntity(policyToSave);
        verify(insurancePolicyRepository).save(entityToSave);
        verify(insurancePolicyMapper).toDomain(savedEntity);
    }
}