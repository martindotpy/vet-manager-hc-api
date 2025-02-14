package com.vluepixel.vetmanager.api.patient.race.domain.request;

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
 * Create race request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateRaceRequest implements Request {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotNull(message = "El id de la especie no puede ser nulo")
    @Positive(message = "El id de la especie debe ser mayor a 0")
    private Integer speciesId;
}
