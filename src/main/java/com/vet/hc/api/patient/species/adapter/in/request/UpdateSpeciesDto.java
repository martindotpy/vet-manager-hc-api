package com.vet.hc.api.patient.species.adapter.in.request;

import org.hibernate.validator.constraints.Length;

import com.vet.hc.api.patient.species.domain.payload.UpdateSpeciesPayload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request payload to update a species.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateSpeciesDto implements UpdateSpeciesPayload {
    @NotNull(message = "El id es requerido")
    @Min(value = 1, message = "El id no puede ser menor a 1")
    private Long id;

    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Length(max = 12, message = "El nombre no puede tener más de 12 caracteres")
    private String name;
}
