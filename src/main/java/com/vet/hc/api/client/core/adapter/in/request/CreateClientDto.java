package com.vet.hc.api.client.core.adapter.in.request;

import com.vet.hc.api.client.core.domain.enums.IdentificationType;
import com.vet.hc.api.client.core.domain.payload.CreateClientPayload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a request to create a client.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientDto implements CreateClientPayload {
    @NotNull(message = "El nombre del cliente es requerido")
    @NotEmpty(message = "El nombre del cliente no puede estar vacío")
    @NotBlank(message = "El nombre del cliente no puede estar en blanco")
    @Size(min = 2, message = "El nombre del cliente debe tener al menos 2 caracteres")
    @Size(max = 50, message = "El nombre del cliente debe tener como máximo 50 caracteres")
    private String firstName;
    @NotNull(message = "El apellido del cliente es requerido")
    @NotEmpty(message = "El apellido del cliente no puede estar vacío")
    @NotBlank(message = "El apellido del cliente no puede estar en blanco")
    @Size(min = 2, message = "El apellido del cliente debe tener al menos 2 caracteres")
    @Size(max = 50, message = "El apellido del cliente debe tener como máximo 50 caracteres")
    private String lastName;
    @NotNull(message = "La identificación es requerida")
    private String identification;
    @NotNull(message = "El tipo de identificación es requerido")
    private IdentificationType identificationType;
    @NotNull(message = "La dirección es requerida")
    @NotEmpty(message = "La dirección no puede estar vacía")
    @NotBlank(message = "La dirección no puede estar en blanco")
    @Size(min = 2, message = "La dirección debe tener al menos 2 caracteres")
    @Size(max = 125, message = "La dirección debe tener como máximo 125 caracteres")
    private String address;
}
