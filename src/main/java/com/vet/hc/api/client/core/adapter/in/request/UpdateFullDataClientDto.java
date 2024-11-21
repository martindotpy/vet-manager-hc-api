package com.vet.hc.api.client.core.adapter.in.request;

import java.util.Set;

import com.vet.hc.api.client.core.domain.enums.IdentificationType;
import com.vet.hc.api.client.core.domain.payload.UpdateFullDataClientPayload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a request to update a data client.
 *
 * <p>
 * If a phone or email is removed from the set, it will be deleted from the
 * database.
 * </p>
 * <p>
 * If a phone or email is added to the set without an ID, it will be inserted
 * into the
 * database.
 * </p>
 * <p>
 * If a phone or email is added to the set with an ID, it will be updated in the
 * database.
 * </p>
 * <p>
 * If the client has no phones, the phones set will be empty. The same applies
 * to the emails set.
 * </p>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFullDataClientDto implements UpdateFullDataClientPayload {
    @NotNull(message = "El ID del cliente es requerido")
    @Min(value = 1, message = "El ID del cliente debe ser mayor a 0")
    private Long id;

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

    private Set<@Email(message = "El correo debe ser válido") @NotNull(message = "El correo es requerido") @NotEmpty(message = "El correo no puede estar vacío") @NotBlank(message = "El correo no puede estar en blanco") @Size(max = 50, message = "El correo debe tener como máximo 50 caracteres") String> emails;
    private Set<@NotNull(message = "El teléfono es requerido") @NotEmpty(message = "El teléfono no puede estar vacío") @NotBlank(message = "El teléfono no puede estar en blanco") @Size(min = 9, message = "El teléfono debe tener al menos 9 caracteres") @Size(max = 15, message = "El teléfono debe tener como máximo 15 caracteres") @Pattern(regexp = "\\d+", message = "El teléfono solo puede contener números") String> phones;
}
