package com.vet.hc.api.appointment.type.adapter.in.request;

import org.hibernate.validator.constraints.Length;

import com.vet.hc.api.appointment.type.domain.payload.CreateAppointmentTypePayload;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new appointment type.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAppointmentTypeDto implements CreateAppointmentTypePayload {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Length(max = 12, message = "El nombre no puede tener más de 12 caracteres")
    private String name;
    @NotNull(message = "La duración en minutos es requerida")
    @Max(value = 240, message = "La duración en minutos no puede ser mayor a 240 (4 horas)")
    private Integer durationInMinutes;
    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    @DecimalMax(value = "999.99", message = "El precio no puede ser mayor a 999.99")
    @Digits(integer = 3, fraction = 2, message = "El precio no puede tener más de 3 dígitos enteros y 2 decimales")
    private Double price;
}