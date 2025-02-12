package com.vet.hc.api.shared.domain.exception;

public abstract class ErrorException extends RuntimeException {
    public abstract int getStatus();
}
