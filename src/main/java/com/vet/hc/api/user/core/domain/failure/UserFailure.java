package com.vet.hc.api.user.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

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
    NOT_FOUND("User not found"),
    EMAIL_ALREADY_IN_USE("Email already in use"),
    UNEXPECTED("Ha ocurrido un error inesperado");

    private final String message;
}
