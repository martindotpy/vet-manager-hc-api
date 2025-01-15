package com.vet.hc.api.patient.race.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Race failures.
 */
@Getter
@AllArgsConstructor
public enum RaceFailure implements Failure {
    NOT_FOUND("Race not found"),
    DUPLICATED_NAME("Race name already exists"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
