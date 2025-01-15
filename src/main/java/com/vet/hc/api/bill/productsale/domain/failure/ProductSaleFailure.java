package com.vet.hc.api.bill.productsale.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for product sale operations.
 */
@Getter
@AllArgsConstructor
public enum ProductSaleFailure implements Failure {
    NOT_FOUND("Venta de tratamiento no encontrada"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
