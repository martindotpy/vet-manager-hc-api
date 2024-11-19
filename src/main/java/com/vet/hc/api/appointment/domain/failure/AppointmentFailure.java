package com.vet.hc.api.appointment.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for appointment operations.
 */
@Getter
@AllArgsConstructor
public enum AppointmentFailure implements Failure {
    NOT_FOUND("Appointment no encontrado"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
