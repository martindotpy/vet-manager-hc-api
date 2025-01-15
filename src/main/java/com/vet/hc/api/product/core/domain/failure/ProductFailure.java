package com.vet.hc.api.product.core.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Enum for product failures.
 */
@Getter
@AllArgsConstructor
public enum ProductFailure implements Failure {
    NOT_FOUND("Producto no encontrada"),
    DUPLICATE("Producto duplicado"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    @Setter
    private String message;
}
