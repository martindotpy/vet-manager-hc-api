package com.vet.hc.api.patient.vaccine.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for vaccine operations.
 */
@Getter
@AllArgsConstructor
public enum VaccineFailure implements Failure {
    NOT_FOUND("Vacuna no encontrada"),
    UNEXPECTED("Unexpected error");

    private final String message;
}
