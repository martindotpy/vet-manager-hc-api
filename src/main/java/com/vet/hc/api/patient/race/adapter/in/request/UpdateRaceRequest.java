package com.vet.hc.api.patient.race.adapter.in.request;

import com.vet.hc.api.patient.race.domain.payload.UpdateRacePayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to update a race.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateRaceRequest implements UpdateRacePayload {
    @NotNull(message = "El id es requerido")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 12, message = "El nombre no puede tener más de 12 caracteres")
    private String name;

    @NotNull(message = "La especie es requerida")
    @Min(value = 1, message = "La especie no puede ser menor a 1")
    private Long speciesId;
}
