package com.vet.hc.api.medicalrecord.treatment.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Failure for treatment operations.
 */
@Getter
@AllArgsConstructor
public enum TreatmentFailure implements Failure {
    NOT_FOUND("Tratamiento no encontrado"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
