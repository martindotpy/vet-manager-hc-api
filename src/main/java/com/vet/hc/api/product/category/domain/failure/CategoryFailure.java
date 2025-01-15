package com.vet.hc.api.product.category.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for category failures.
 */
@Getter
@AllArgsConstructor
public enum CategoryFailure implements Failure {
    NOT_FOUND("Categoría no encontrada"),
    DUPLICATE("Categoría duplicada"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private String message;
}
