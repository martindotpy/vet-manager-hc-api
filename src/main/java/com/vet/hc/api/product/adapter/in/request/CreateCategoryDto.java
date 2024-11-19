package com.vet.hc.api.product.adapter.in.request;

import com.vet.hc.api.product.domain.payload.CreateCategoryPayload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request object for creating a category.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateCategoryDto implements CreateCategoryPayload {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;
}
