package com.vluepixel.vetmanager.api.auth.core.domain.exception;

import com.vluepixel.vetmanager.api.shared.domain.exception.ErrorException;

import lombok.Getter;

@Getter
public final class EmailAlreadyInUseException extends ErrorException {
    private final String message = "El email ya está en uso";
    private final int status = 409;
}
