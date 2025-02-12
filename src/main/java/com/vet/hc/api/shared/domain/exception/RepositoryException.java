package com.vet.hc.api.shared.domain.exception;

import lombok.Getter;

@Getter
public final class RepositoryException extends RuntimeException {
    private final Class<?> entityClass;

    public RepositoryException(Throwable cause, Class<?> entityClass) {
        super(cause);
        this.entityClass = entityClass;
    }
}
