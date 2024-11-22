package com.vet.hc.api.bill.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Appointment type failures.
 */
@Getter
@AllArgsConstructor
public enum BillFailure implements Failure {
    NOT_FOUND("El tipo de cita no existe"),
    DUPLICATED_NAME("El nombre del tipo de cita ya existe"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
