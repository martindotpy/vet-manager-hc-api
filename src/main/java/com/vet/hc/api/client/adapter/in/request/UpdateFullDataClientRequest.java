package com.vet.hc.api.client.adapter.in.request;

import java.util.Set;

import jakarta.validation.Valid;
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
public class UpdateFullDataClientRequest {
    @NotNull(message = "El cliente es requerido")
    @Valid
    private UpdateClientRequest client;
    @NotNull(message = "El correo es requerido")
    @Valid
    private Set<UpdateClientEmailRequest> emails;
    @NotNull(message = "El teléfono es requerido")
    @Valid
    private Set<UpdateClientPhoneRequest> phones;

    @Getter
    public class UpdateClientRequest extends CreateClientRequest {
        @NotNull(message = "El ID del cliente es requerido")
        @Min(value = 1, message = "El ID del cliente debe ser mayor a 0")
        private Long id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateClientEmailRequest {
        @Min(value = 1, message = "El ID del correo debe ser mayor a 0")
        private Long id;
        @Email(message = "El correo debe ser válido")
        @NotNull(message = "El correo es requerido")
        @NotEmpty(message = "El correo no puede estar vacío")
        @NotBlank(message = "El correo no puede estar en blanco")
        @Size(max = 50, message = "El correo debe tener como máximo 50 caracteres")
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateClientPhoneRequest {
        @Min(value = 1, message = "El ID del teléfono debe ser mayor a 0")
        private Long id;
        @NotNull(message = "El teléfono es requerido")
        @NotEmpty(message = "El teléfono no puede estar vacío")
        @NotBlank(message = "El teléfono no puede estar en blanco")
        @Size(min = 9, message = "El teléfono debe tener al menos 9 caracteres")
        @Size(max = 15, message = "El teléfono debe tener como máximo 15 caracteres")
        @Pattern(regexp = "\\d+", message = "El teléfono solo puede contener números")
        private String phone;
    }
}
