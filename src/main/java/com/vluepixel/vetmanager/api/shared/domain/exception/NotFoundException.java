package com.vluepixel.vetmanager.api.shared.domain.exception;

import static com.vluepixel.vetmanager.api.shared.domain.util.SpanishUtils.getName;

import lombok.Getter;

/**
 * Not found exception.
 */
@Getter
public final class NotFoundException extends ErrorException {
    private String message = "% no encontrado(a)";
    private final int status = 404;

    public NotFoundException() {
        this.message = this.message.replace("%", "Recurso");
    }

    public NotFoundException(String entity) {
        this.message = this.message.replace("%", getName(entity));
    }

    public NotFoundException(Class<?> entity) {
        this(getName(entity));
    }

    public NotFoundException(String entity, Object id) {
        this.message = this.message.replace("%", getName(entity) + " con id " + id);
    }

    public NotFoundException(Class<?> entity, Object id) {
        this(getName(entity), id);
    }

    public NotFoundException(String entity, String field, Object value) {
        this.message = this.message.replace("%", getName(entity) + " con " + getName(field) + " " + value);
    }

    public NotFoundException(Class<?> entity, String field, Object value) {
        this(getName(entity), getName(field), value);
    }
}
