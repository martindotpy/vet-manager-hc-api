package com.vluepixel.vetmanager.api.appointment.details.domain.model;

import java.math.BigDecimal;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.vluepixel.vetmanager.api.appointment.type.domain.model.AppointmentType;
import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment details.
 */
@Entity
@Audited
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Detalles de la cita")
public final class AppointmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Max(1440)
    private int durationInMinutes;
    @NotNull
    @DecimalMax(value = "9999.99")
    @Positive
    @Column(columnDefinition = "decimal(4, 2)")
    private BigDecimal price;

    @NotNull
    @ManyToOne
    @NotAudited
    private AppointmentType type;
}
