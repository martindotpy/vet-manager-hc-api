package com.vluepixel.vetmanager.api.shared.domain.exception;

import static com.vluepixel.vetmanager.api.shared.domain.util.SpanishUtils.getName;

import lombok.Getter;

@Getter
public final class NotFoundException extends ErrorException {
    private String message = "% not found";
    private final int status = 404;

    public NotFoundException() {
        this.message = this.message.replace("%", "Resource");
    }

    public NotFoundException(String entity) {
        this.message = this.message.replace("%", entity);
    }

    public NotFoundException(Class<?> entity) {
        this(getName(entity));
    }

    public NotFoundException(String entity, Object id) {
        this.message = this.message.replace("%", entity + " with id " + id);
    }

    public NotFoundException(Class<?> entity, Object id) {
        this(getName(entity), id);
    }

    public NotFoundException(String entity, String field, Object value) {
        this.message = this.message.replace("%", entity + " with " + field + " " + value);
    }

    public NotFoundException(Class<?> entity, String field, Object value) {
        this(getName(entity), field, value);
    }
}
