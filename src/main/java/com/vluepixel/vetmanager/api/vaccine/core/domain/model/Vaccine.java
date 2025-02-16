package com.vluepixel.vetmanager.api.vaccine.core.domain.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Vaccine.
 */
@Entity
@Audited
@SQLDelete(sql = "UPDATE vaccine SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Vacuna")
public final class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long id;

    @Size(max = 50)
    @NotBlank
    @Column(columnDefinition = "varchar(50)")
    @SpanishName("Nombre")
    private String name;
    @Max(250)
    @NotNull
    @Positive
    @Column(columnDefinition = "tinyint unsigned")
    @SpanishName("Dosis en mililitros")
    private Integer doseInMilliliters;
    @NotNull
    @SpanishName("Fecha de aplicación")
    private LocalDateTime providedAt;

    @NotNull
    @ManyToOne
    @SpanishName("Paciente")
    private Patient patient;
    @NotNull
    @ManyToOne
    @SpanishName("Vacunador")
    private User vaccinator;
    // TODO:
    // @ManyToOne
    // private ProductSale sale;

    @Builder.Default
    private boolean deleted = false;
}
