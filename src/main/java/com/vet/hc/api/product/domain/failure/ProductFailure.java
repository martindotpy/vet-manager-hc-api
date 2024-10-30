package com.vet.hc.api.product.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for product failures.
 */
@Getter
@AllArgsConstructor
public enum ProductFailure implements Failure {
    NOT_FOUND("Producto no encontrada"),
    DUPLICATE("Producto duplicado"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    UNEXPECTED("Error inesperado");

    private String message;
}
