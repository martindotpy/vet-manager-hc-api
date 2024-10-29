package com.vet.hc.api.client.domain.failure;

import com.vet.hc.api.shared.domain.query.Failure;

import lombok.Getter;

/**
 * Failure for client operations.
 */
@Getter
public enum ClientFailure implements Failure {
    NOT_FOUND("Cliente no encontrado"),;

    private String message;

    private ClientFailure(String message) {
        this.message = message;
    }
}
