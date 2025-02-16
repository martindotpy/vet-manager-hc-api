package com.vluepixel.vetmanager.api.user.core.domain.request;

import com.vluepixel.vetmanager.api.image.core.domain.model.enums.ImageMimeType;
import com.vluepixel.vetmanager.api.shared.domain.request.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Update user profile image request.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateUserProfileImageRequest implements Request {
    @NotNull(message = "El tipo de imagen es requerido")
    private ImageMimeType type;
    @NotNull(message = "Los datos de la imagen son requeridos")
    private byte[] data;

    @NotNull(message = "El id del usuario es requerido")
    @Positive(message = "El id del usuario debe ser mayor a 0")
    private Long userId;
}
