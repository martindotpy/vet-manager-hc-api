package com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

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
 * Medical history entity.
 */
@Entity
@Table(name = "medical_history")
@SQLDelete(sql = "UPDATE medical_history SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedMedicalHistoryFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedMedicalHistoryFilter", condition = "deleted = :isDeleted")
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

    @Builder.Default
    private boolean deleted = false;
}
