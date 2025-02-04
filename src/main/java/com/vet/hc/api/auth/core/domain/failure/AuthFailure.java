package com.vet.hc.api.auth.core.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumerates the possible failures of the authentication domain.
 */
@Getter
@RequiredArgsConstructor
public enum AuthFailure implements Failure {
    INVALID_CREDENTIALS("Credenciales inválidas"),
    ALREADY_AUTHENTICATED("Usuario ya autenticado"),
    EMAIL_ALREADY_IN_USE("Correo ya en uso"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
