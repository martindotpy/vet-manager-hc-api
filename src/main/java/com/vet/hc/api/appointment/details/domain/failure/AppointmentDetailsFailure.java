package com.vet.hc.api.appointment.details.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for appointment details operations.
 */
@Getter
@AllArgsConstructor
public enum AppointmentDetailsFailure implements Failure {
    NOT_FOUND("Appointment no encontrado"),
    APPOINTMENT_NOT_FOUND("Cita no encontrada"),
    APPOINTMENT_TYPE_NOT_FOUND("Tipo de cita no encontrado"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
