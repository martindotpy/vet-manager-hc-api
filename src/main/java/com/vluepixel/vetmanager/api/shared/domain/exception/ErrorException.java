package com.vluepixel.vetmanager.api.shared.domain.exception;

/**
 * Error exception.
 */
public abstract class ErrorException extends RuntimeException {
    public abstract int getStatus();
}
