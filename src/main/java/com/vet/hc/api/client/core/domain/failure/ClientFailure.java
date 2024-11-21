package com.vet.hc.api.client.core.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;

/**
 * Failure for client operations.
 */
@Getter
public enum ClientFailure implements Failure {
    NOT_FOUND("Cliente no encontrado"),
    EMAIL_ALREADY_IN_USE("El correo electrónico ya está en uso"),
    PHONE_ALREADY_IN_USE("El teléfono ya está en uso"),
    EMAIL_SAVE_ERROR("Error al guardar los correos electrónicos"),
    PHONE_SAVE_ERROR("Error al guardar los teléfonos"),
    FIELD_NOT_FOUND("Campo no encontrado"),
    UNEXPECTED("Error inesperado");

    private String message;

    private ClientFailure(String message) {
        this.message = message;
    }
}
