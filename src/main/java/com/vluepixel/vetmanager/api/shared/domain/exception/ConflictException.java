package com.vluepixel.vetmanager.api.shared.domain.exception;

import static com.vluepixel.vetmanager.api.shared.domain.util.SpanishUtils.getName;

import lombok.Getter;

@Getter
public final class ConflictException extends ErrorException {
    private String message = "% debe ser único(a)";
    private final int status = 409;

    public ConflictException() {
        this.message = this.message.replace("%", "El recurso");
    }

    public ConflictException(String entity) {
        this.message = this.message.replace("%", entity);
    }

    public ConflictException(Class<?> entity) {
        this(getName(entity));
    }

    public ConflictException(String entity, String field) {
        this.message = this.message.replace("%", field + " de " + entity.toLowerCase());
    }

    public ConflictException(Class<?> entity, String field) {
        this(getName(entity), getName(entity, field));
    }
}
