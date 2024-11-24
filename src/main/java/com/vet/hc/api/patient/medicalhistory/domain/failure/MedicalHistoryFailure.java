package com.vet.hc.api.patient.medicalhistory.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for medical history operations.
 */
@Getter
@AllArgsConstructor
public enum MedicalHistoryFailure implements Failure {
    NOT_FOUND("Vacuna no encontrada"),
    UNEXPECTED("Unexpected error");

    private final String message;
}
