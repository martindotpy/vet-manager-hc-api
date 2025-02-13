package com.vluepixel.vetmanager.api.appointment.type.domain.model;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.vluepixel.vetmanager.api.shared.domain.annotation.SpanishName;

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

@Entity
@Getter
@SQLDelete(sql = "UPDATE appointment_type SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpanishName("Tipo de cita")
public final class AppointmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(columnDefinition = "VARCHAR(20)")
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
