package com.vluepixel.vetmanager.api.medicalrecord.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;
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
@SpanishName("Registro médico")
public final class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @NotBlank
    @Column(columnDefinition = "text")
    @SpanishName("Motivo")
    private String reason;
    @NotNull
    @SpanishName("Fecha de ingreso")
    private LocalDateTime entryAt;
    @Column(columnDefinition = "text")
    @SpanishName("Examen físico")
    private String physicalExam;
    @NotNull
    @Positive
    @DecimalMax(value = "100.0")
    @Column(columnDefinition = "decimal(4, 1)")
    @SpanishName("Temperatura en Celsius")
    private BigDecimal temperatureInCelsius;
    @NotNull
    @Positive
    @Column(columnDefinition = "tinyint unsigned")
    @SpanishName("Frecuencia respiratoria")
    private Integer respitarionRate;
    @NotNull
    @Positive
    @Max(1000)
    @Column(columnDefinition = "smallint unsigned")
    @SpanishName("Frecuencia cardíaca")
    private Integer heartRate;
    @NotNull
    @Positive
    @DecimalMax(value = "999.99")
    @Column(columnDefinition = "decimal(5, 2)")
    @SpanishName("Peso")
    private BigDecimal weight;
    @SpanishName("Esterilizado")
    private boolean sterilized;
    @Column(columnDefinition = "text")
    @SpanishName("Receta")
    private String recipe;
    @Column(columnDefinition = "text")
    @SpanishName("Diagnóstico")
    private String diagnosis;

    @NotNull
    @ManyToOne
    @SpanishName("Paciente")
    private Patient patient;
    @NotNull
    @ManyToOne
    @SpanishName("Veterinario")
    private User vet;
    @OneToMany(mappedBy = "record")
    @SpanishName("Tratamientos")
    private List<@NotNull Treatment> treatments;

    @Builder.Default
    private boolean deleted = false;
}
