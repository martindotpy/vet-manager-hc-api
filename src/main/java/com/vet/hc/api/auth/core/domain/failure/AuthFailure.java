package com.vet.hc.api.auth.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents an authentication failure.
 */
@Getter
@RequiredArgsConstructor
public enum AuthFailure implements Failure {
    EMAIL_ALREADY_IN_USE("Correo ya en uso"),
    INVALID_CREDENTIALS("Credenciales inválidas"),;

    private final String message;
}
