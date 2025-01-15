package com.vet.hc.api.patient.medicalhistory.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for medical history operations.
 */
@Getter
@AllArgsConstructor
public enum MedicalHistoryFailure implements Failure {
    NOT_FOUND("Vacuna no encontrada"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
