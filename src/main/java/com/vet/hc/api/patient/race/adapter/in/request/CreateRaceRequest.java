package com.vet.hc.api.patient.race.adapter.in.request;

import com.vet.hc.api.patient.race.domain.payload.CreateRacePayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new race.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateRaceRequest implements CreateRacePayload {
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 12, message = "El nombre no puede tener más de 12 caracteres")
    private String name;

    @NotNull(message = "El id de la especie es requerido")
    @Min(value = 1, message = "El id de la especie no puede ser menor a 1")
    private Long speciesId;
}
