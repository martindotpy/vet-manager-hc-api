package com.vluepixel.vetmanager.api.auth.core.domain.exception;

import com.vluepixel.vetmanager.api.shared.domain.exception.ErrorException;

import lombok.Getter;

@Getter
public final class UserAlreadyAuthenticatedException extends ErrorException {
    private final String message = "Usuario ya autenticado";
    private final int status = 409;
}
