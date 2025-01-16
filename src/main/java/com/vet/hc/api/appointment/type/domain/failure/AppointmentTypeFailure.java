package com.vet.hc.api.appointment.type.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Appointment type failures.
 */
@Getter
@AllArgsConstructor
public enum AppointmentTypeFailure implements Failure {
    NOT_FOUND("El tipo de cita no existe"),
    DUPLICATED_NAME("El nombre del tipo de cita ya está en uso"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
