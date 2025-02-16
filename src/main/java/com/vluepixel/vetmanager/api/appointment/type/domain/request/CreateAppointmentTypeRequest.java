package com.vluepixel.vetmanager.api.appointment.type.domain.request;

import java.math.BigDecimal;

import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Create appointment type request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAppointmentTypeRequest implements Request {
    @NotBlank(message = "El nombre del tipo de cita es requerido")
    @Size(max = 20, message = "El nombre del tipo de cita no puede tener más de 20 caracteres")
    private String name;
    @Max(value = 720, message = "La duración de la cita no puede ser mayor a 720 minutos")
    @Positive(message = "La duración de la cita debe ser mayor a 0")
    private int durationInMinutes;
    @DecimalMax(value = "999.99", message = "El precio de la cita no puede ser mayor a 999.99")
    @Positive(message = "El precio de la cita debe ser mayor a 0")
    private BigDecimal price;
}
