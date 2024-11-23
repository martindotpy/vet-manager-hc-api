package com.vet.hc.api.appointment.details.adapter.in.request;

import com.vet.hc.api.appointment.details.domain.payload.CreateAppointmentDetailsPayload;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new appointment details.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateAppointmentDetailsDto implements CreateAppointmentDetailsPayload {
    @NotNull(message = "La duración en minutos es requerida")
    @Max(value = 240, message = "La duración en minutos no puede ser mayor a 240 (4 horas)")
    private Integer durationInMinutes;
    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    @DecimalMax(value = "999.99", message = "El precio no puede ser mayor a 999.99")
    @Digits(integer = 3, fraction = 2, message = "El precio no puede tener más de 3 dígitos enteros y 2 decimales")
    private Double price;

    @NotNull(message = "El id de la cita es requerido")
    @Min(value = 1, message = "El id de la cita no puede ser menor a 1")
    private Long appointmentId;
    @NotNull(message = "El id del tipo de cita es requerido")
    @Min(value = 1, message = "El id del tipo de cita no puede ser menor a 1")
    private Long appointmentTypeId;
}
