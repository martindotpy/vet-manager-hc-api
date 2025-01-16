package com.vet.hc.api.appointment.details.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for appointment details operations.
 */
@Getter
@AllArgsConstructor
public enum AppointmentDetailsFailure implements Failure {
    NOT_FOUND("Detalles de cita no encontrados"),
    APPOINTMENT_NOT_FOUND("Cita no encontrada"),
    APPOINTMENT_TYPE_NOT_FOUND("Tipo de cita no encontrada"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
