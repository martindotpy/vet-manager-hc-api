package com.vet.hc.api.auth.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;

/**
 * Represents an authentication failure.
 */
@Getter
public enum AuthFailure implements Failure {
    EMAIL_ALREADY_IN_USE("Correo ya en uso"),
    INVALID_CREDENTIALS("Credenciales inválidas"),;

    private String message;

    private AuthFailure(String message) {
        this.message = message;
    }
}
