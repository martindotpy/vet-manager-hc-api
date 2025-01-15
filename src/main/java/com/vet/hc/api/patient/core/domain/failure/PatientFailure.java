package com.vet.hc.api.patient.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for patient operations.
 */
@Getter
@AllArgsConstructor
public enum PatientFailure implements Failure {
    NOT_FOUND("Paciente no encontrado"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    CLIENT_NOT_FOUND("Cliente no encontrado"),
    RACE_NOT_FOUND("Raza no encontrada"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
