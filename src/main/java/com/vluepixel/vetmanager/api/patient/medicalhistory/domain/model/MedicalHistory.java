package com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Medical history.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE medical_history SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Historial médico")
public final class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @NotBlank
    @Column(columnDefinition = "text")
    @SpanishName("Contenido")
    private String content;
    @CreationTimestamp
    @SpanishName("Fecha de creación")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @SpanishName("Fecha de actualización")
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne
    @SpanishName("Paciente")
    private Patient patient;

    @Builder.Default
    private boolean deleted = false;
}
