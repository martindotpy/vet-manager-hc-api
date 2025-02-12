package com.vet.hc.api.user.core.domain.exception;

import com.vet.hc.api.shared.domain.exception.ErrorException;

import lombok.Getter;

@Getter
public final class UserCannotDeleteItselfException extends ErrorException {
    private final String message = "El usuario no se puede borrar así mismo";
    private final int status = 403;
}
