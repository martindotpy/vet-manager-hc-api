package com.vet.hc.api.product.core.adapter.in.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vet.hc.api.product.category.domain.payload.CreateProductPayload;
import com.vet.hc.api.product.core.adapter.in.validator.PriceExtendedValidation;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request object for creating a product.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({ CreateProductDto.class, PriceExtendedValidation.class })
public final class CreateProductDto implements CreateProductPayload {
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;
    @NotNull(message = "El precio es requerido")
    private Double price;
    @NotNull(message = "La descripción es requerida")
    @NotEmpty(message = "La descripción no puede estar vacía")
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String description;
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer quantity;
    @NotNull(message = "Las categorías son requeridas")
    @NotEmpty(message = "Las categorías no pueden estar vacías")
    private Set<@Min(value = 1, message = "El identificador de la categoría no puede ser negativo") Long> categoryIds;

    /**
     * Validates if the price is less than 0.10 cents.
     *
     * @return true if the price is less than 0.10 cents, false otherwise
     */
    @JsonIgnore
    @AssertFalse(message = "El precio no puede ser menor a 0.10 céntimos", groups = PriceExtendedValidation.class)
    public boolean isPriceLessThanTenCents() {
        return price < 0.10;
    }
}
