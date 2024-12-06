package com.vet.hc.api.patient.species.adapter.in.request;

import com.vet.hc.api.patient.species.domain.payload.CreateSpeciesPayload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to create a new species.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateSpeciesRequest implements CreateSpeciesPayload {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 12, message = "El nombre no puede tener más de 12 caracteres")
    private String name;
}
