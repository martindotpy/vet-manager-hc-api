package com.vluepixel.vetmanager.api.patient.core.domain.request;

import java.time.LocalDate;

import com.vluepixel.vetmanager.api.patient.core.domain.enums.PatientGender;
import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update patient request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdatePatientRequest implements Request {
    @NotNull(message = "El id no puede estar vacío")
    @Positive(message = "El id debe ser un número positivo")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;
    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    private LocalDate birthDate;
    private PatientGender gender;
    private String characteristics;
    private boolean deceased;

    @NotNull(message = "El id de la raza no puede ser nulo")
    @Positive(message = "El id de la raza debe ser mayor a 0")
    private Integer raceId;
    @NotNull(message = "El id del dueño no puede ser nulo")
    @Positive(message = "El id del dueño debe ser mayor a 0")
    private Long ownerId;
}
