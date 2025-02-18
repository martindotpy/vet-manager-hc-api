package com.vluepixel.vetmanager.api.appointment.type.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
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
 * Appointment type.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "tinyint unsigned")
    private Integer id;

    @NotBlank
    @Size(max = 20)
    @Column(columnDefinition = "varchar(20)")
    private String name;
    @NotNull
    @Positive
    @Max(720)
    private int durationInMinutes;
    @NotNull
    @DecimalMax(value = "999.99")
    @Positive
    @Column(columnDefinition = "decimal(3, 2)")
    private BigDecimal price;
}
