package com.tinubu.insurancepolicy.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "INSURANCE_POLICIES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "policy_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status")
    private InsurancePolicyStatusEntity status;

    @Column(name = "coverage_start_date", nullable = false)
    private LocalDate coverageStartDate;

    @Column(name = "coverage_end_date", nullable = false)
    private LocalDate coverageEndDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
