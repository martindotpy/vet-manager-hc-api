package com.vet.hc.api.medicalrecord.core.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for medical record operations.
 */
@Getter
@AllArgsConstructor
public enum MedicalRecordFailure implements Failure {
    NOT_FOUND("Registro historial no encontrada"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
