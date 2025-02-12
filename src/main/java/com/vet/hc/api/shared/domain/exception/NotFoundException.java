package com.vet.hc.api.shared.domain.exception;

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

    public NotFoundException(String entity, Object id) {
        this.message = this.message.replace("%", entity + " with id " + id);
    }

    public NotFoundException(String entity, String field, Object value) {
        this.message = this.message.replace("%", entity + " with " + field + " " + value);
    }
}
