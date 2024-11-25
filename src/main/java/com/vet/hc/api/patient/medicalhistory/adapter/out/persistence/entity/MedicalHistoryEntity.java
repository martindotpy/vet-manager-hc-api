package com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MedicalHistory entity.
 */
@Entity
@Table(name = "medical_history")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private PatientEntity patient;
}
