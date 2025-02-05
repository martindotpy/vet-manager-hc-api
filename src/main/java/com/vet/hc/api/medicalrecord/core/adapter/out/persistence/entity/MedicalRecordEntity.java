package com.vet.hc.api.medicalrecord.core.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.entity.TreatmentEntity;
import com.vet.hc.api.patient.core.adapter.out.persistence.entity.PatientEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MedicalRecord entity.
 */
@Entity
@Table(name = "medical_record")
@SQLDelete(sql = "UPDATE medical_record SET deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "deletedMedicalRecordFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedMedicalRecordFilter", condition = "deleted = :isDeleted")
public class MedicalRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime entryTime;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String reason;
    @Column(columnDefinition = "text")
    private String physicalExamination;
    @Column(columnDefinition = "decimal(4, 1)", nullable = false)
    private Float celsiusTemperature;
    private Integer respiratoryRate;
    @Column(columnDefinition = "decimal(4, 1)")
    private Float heartRate;
    @Column(columnDefinition = "decimal(5, 2)", nullable = false)
    private Float weight;
    @Column(columnDefinition = "bit(1) default 0", nullable = false)
    private boolean sterilized;
    @Column(columnDefinition = "text")
    private String supplementaryExamination;
    @Column(columnDefinition = "text")
    private String recipe;
    @Column(columnDefinition = "text")
    private String diagnosis;

    @ManyToOne
    private PatientEntity patient;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity vet;
    @OneToMany(mappedBy = "medicalRecord", fetch = FetchType.EAGER)
    private List<TreatmentEntity> treatments;

    @Builder.Default
    private boolean deleted = false;
}
