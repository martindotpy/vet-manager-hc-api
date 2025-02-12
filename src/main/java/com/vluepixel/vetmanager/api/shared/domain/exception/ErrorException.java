package com.vluepixel.vetmanager.api.shared.domain.exception;

public abstract class ErrorException extends RuntimeException {
    public abstract int getStatus();
}
