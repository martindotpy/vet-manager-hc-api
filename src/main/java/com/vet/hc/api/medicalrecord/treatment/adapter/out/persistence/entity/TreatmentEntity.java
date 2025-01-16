package com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.vet.hc.api.medicalrecord.core.adapter.out.persistence.entity.MedicalRecordEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Treatment entity.
 */
@Entity
@Table(name = "treatment")
@SQLDelete(sql = "UPDATE treatment SET deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedTreatmentFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedTreatmentFilter", condition = "deleted = :isDeleted")
public class TreatmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`order`", columnDefinition = "tinyint unsigned", nullable = false)
    private Integer order;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecordEntity medicalRecord;

    @Builder.Default
    private boolean deleted = false;
}
