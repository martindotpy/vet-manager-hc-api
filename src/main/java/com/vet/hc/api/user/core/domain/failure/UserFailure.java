package com.vet.hc.api.user.core.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * User failures.
 */
@Getter
@RequiredArgsConstructor
public enum UserFailure implements Failure {
    NOT_FOUND("Usuario no encontrado"),
    EMAIL_ALREADY_IN_USE("Correo ya en uso"),
    FIRST_NAME_REQUIRED("Nombre es requerido"),
    LAST_NAME_REQUIRED("Apellido es requerido"),
    USER_CANNOT_DELETE_THEMSELF("No puede eliminarse a sí mismo"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
