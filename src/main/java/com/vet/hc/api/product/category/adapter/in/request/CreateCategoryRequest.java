package com.vet.hc.api.product.category.adapter.in.request;

import com.vet.hc.api.product.core.domain.payload.CreateCategoryPayload;

import jakarta.validation.constraints.NotBlank;
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
public final class CreateCategoryRequest implements CreateCategoryPayload {
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;
}
