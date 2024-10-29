package com.vet.hc.api.client.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;

/**
 * Failure for client operations.
 */
@Getter
public enum ClientFailure implements Failure {
    NOT_FOUND("Cliente no encontrado"),
    ID_MISSMATCH("El id del cliente no coincide con el id de la petición"),
    EMAIL_MISSMATCH_WITH_CLIENT("El correo electrónico no pertenece al cliente"),
    PHONE_MISSMATCH_WITH_CLIENT("El teléfono no pertenece al cliente"),
    EMAIL_SAVE_ERROR("Error al guardar los correos electrónicos"),
    PHONE_SAVE_ERROR("Error al guardar los teléfonos"),;

    private String message;

    private ClientFailure(String message) {
        this.message = message;
    }
}
