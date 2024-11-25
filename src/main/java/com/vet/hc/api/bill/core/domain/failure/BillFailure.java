package com.vet.hc.api.bill.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for bill operations.
 */
@Getter
@AllArgsConstructor
public enum BillFailure implements Failure {
    NOT_FOUND("Paciente no encontrado"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    CLIENT_NOT_FOUND("Cliente no encontrado"),
    CANNOT_UPDATE_PAID_BILL("No se puede modificar una cuenta que ya ha sido pagada"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
