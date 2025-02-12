package com.vet.hc.api.auth.core.domain.exception;

import com.vet.hc.api.shared.domain.exception.ErrorException;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public final class InvalidCredentialsException extends ErrorException {
    private final String message = "Credenciales inválidas";
    private final int status = 401;
}
