package com.vet.hc.api.appointment.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for appointment operations.
 */
@Getter
@AllArgsConstructor
public enum AppointmentFailure implements Failure {
    NOT_FOUND("Cita no encontrada"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    PATIENT_NOT_FOUND("Paciente no encontrado"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
