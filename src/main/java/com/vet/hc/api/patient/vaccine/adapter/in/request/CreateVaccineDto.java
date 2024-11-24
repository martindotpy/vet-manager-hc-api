package com.vet.hc.api.patient.vaccine.adapter.in.request;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.vaccine.domain.payload.CreateVaccinePayload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new vaccine.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateVaccineDto implements CreateVaccinePayload {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;
    @NotNull(message = "La dosis es requerida")
    @Max(value = 240, message = "La dosis no puede ser mayor a 240 ml")
    private Integer dose;
    @NotNull(message = "La fecha de vacunación es requerida")
    private LocalDateTime vaccinatedAt;

    @NotNull(message = "El id de la cita es requerido")
    @Min(value = 1, message = "El id de la cita no puede ser menor a 1")
    private Long patientId;
    @NotNull(message = "El id del tipo de cita es requerido")
    @Min(value = 1, message = "El id del tipo de cita no puede ser menor a 1")
    private Long vaccinatorId;
    @NotNull(message = "El id del tipo de cita es requerido")
    @Min(value = 1, message = "El id del tipo de cita no puede ser menor a 1")
    private Long productSaleId;
}
