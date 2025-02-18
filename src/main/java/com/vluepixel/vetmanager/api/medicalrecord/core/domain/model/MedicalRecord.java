package com.vluepixel.vetmanager.api.medicalrecord.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Medical record.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE medical_record SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @NotBlank
    @Column(columnDefinition = "text")
    private String reason;
    @NotNull
    private LocalDateTime entryAt;
    @Column(columnDefinition = "text")
    private String physicalExam;
    @NotNull
    @Positive
    @DecimalMax(value = "100.0")
    @Column(columnDefinition = "decimal(4, 1)")
    private BigDecimal temperatureInCelsius;
    @NotNull
    @Positive
    @Column(columnDefinition = "tinyint unsigned")
    private Integer respitarionRate;
    @NotNull
    @Positive
    @Max(1000)
    @Column(columnDefinition = "smallint unsigned")
    private Integer heartRate;
    @NotNull
    @Positive
    @DecimalMax(value = "999.99")
    @Column(columnDefinition = "decimal(5, 2)")
    private BigDecimal weight;
    private boolean sterilized;
    @Column(columnDefinition = "text")
    private String recipe;
    @Column(columnDefinition = "text")
    private String diagnosis;

    @NotNull
    @ManyToOne
    private Patient patient;
    @NotNull
    @ManyToOne
    private User vet;
    @OneToMany(mappedBy = "medicalRecord")
    private List<@NotNull Treatment> treatments;

    @Builder.Default
    private boolean deleted = false;
}
