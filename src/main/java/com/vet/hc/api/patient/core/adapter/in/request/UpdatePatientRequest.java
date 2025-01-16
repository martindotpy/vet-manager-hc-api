package com.vet.hc.api.patient.core.adapter.in.request;

import java.time.LocalDate;

import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.core.domain.payload.UpdatePatientPayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update patient dto (request).
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdatePatientRequest implements UpdatePatientPayload {
    @NotNull(message = "El id no puede ser nulo")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate birthDate;
    @NotBlank(message = "Las características no pueden estar en blanco")
    private String characteristics;
    @NotNull(message = "El género no puede ser nulo")
    private Genre genre;

    @NotNull(message = "El id del dueño no puede ser nulo")
    @Min(value = 1, message = "El id del dueño no puede ser menor a 1")
    private Long ownerId;
    @NotNull(message = "El id de la raza no puede ser nulo")
    @Min(value = 1, message = "El id de la raza no puede ser menor a 1")
    private Long raceId;
}
