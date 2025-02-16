package com.vluepixel.vetmanager.api.client.core.domain.request;

import java.util.List;

import com.vluepixel.vetmanager.api.client.core.domain.enums.IdentificationType;
import com.vluepixel.vetmanager.api.shared.adapter.in.util.RegexConstants;
import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Create client request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateClientRequest implements Request {
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String firstName;
    @NotBlank(message = "El apellido es requerido")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String lastName;
    @NotBlank(message = "La identificación es requerida")
    @Size(max = 12, message = "La identificación no puede tener más de 12 caracteres")
    private String identification;
    @NotNull(message = "El tipo de identificación es requerido")
    private IdentificationType identificationType;
    @NotBlank(message = "La dirección es requerida")
    @Size(max = 125, message = "La dirección no puede tener más de 125 caracteres")
    private String address;

    private List<@NotBlank(message = "El correo es requerido") @Size(max = 50, message = "El correo no puede tener más de 50 caracteres") @Email(message = "El correo no es válido") String> emails;
    private List<@Pattern(regexp = RegexConstants.PHONE, message = "El teléfono no es válido") @NotBlank(message = "El teléfono es requerido") String> phones;
}
