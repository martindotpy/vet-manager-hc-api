package com.vet.hc.api.patient.species.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Species failures.
 */
@Getter
@AllArgsConstructor
public enum SpeciesFailure implements Failure {
    NOT_FOUND("Species not found"),
    DUPLICATED_NAME("Species name already exists"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
