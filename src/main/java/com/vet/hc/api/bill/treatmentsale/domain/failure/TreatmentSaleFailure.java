package com.vet.hc.api.bill.treatmentsale.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for treatment sale operations.
 */
@Getter
@AllArgsConstructor
public enum TreatmentSaleFailure implements Failure {
    NOT_FOUND("Venta de tratamiento no encontrada"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
