package com.vet.hc.api.product.adapter.in.request;

import com.vet.hc.api.product.adapter.in.validator.PriceExtendedValidation;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object for updating a product.
 */
@Getter
@Builder
@GroupSequence({ UpdateProductRequest.class, PriceExtendedValidation.class })
public class UpdateProductRequest {
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

    /**
     * Validates if the price is less than 0.10 cents.
     *
     * @return true if the price is less than 0.10 cents, false otherwise
     */
    @AssertFalse(message = "El precio no puede ser menor a 0.10 céntimos", groups = PriceExtendedValidation.class)
    public boolean validatePriceLessThanTenCents() {
        return price < 0.10;
    }
}
