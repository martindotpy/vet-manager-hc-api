package com.vet.hc.api.image.core.domain.failure;

import com.vet.hc.api.shared.domain.failure.Failure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Image failure.
 */
@Getter
@RequiredArgsConstructor
public enum ImageFailure implements Failure {
    NOT_FOUND("Imagen no encontrada"),
    UNEXPECTED("Error inesperado");

    private final String message;
}
