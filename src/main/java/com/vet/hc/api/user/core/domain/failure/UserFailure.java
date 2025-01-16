package com.vet.hc.api.user.core.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * User failures.
 *
 * @see Failure
 */
@Getter
@RequiredArgsConstructor
public enum UserFailure implements Failure {
    NOT_FOUND("Usuario no encontrado"),
    EMAIL_ALREADY_IN_USE("El email ya está en uso"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
