package com.vet.hc.api.patient.race.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Race failures.
 */
@Getter
@AllArgsConstructor
public enum RaceFailure implements Failure {
    NOT_FOUND("Raza no encontrada"),
    DUPLICATED_NAME("El nombre de raza ya está en uso"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
