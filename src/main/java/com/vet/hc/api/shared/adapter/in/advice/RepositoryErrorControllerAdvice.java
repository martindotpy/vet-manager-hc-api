package com.vet.hc.api.shared.adapter.in.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vet.hc.api.shared.domain.exception.RepositoryException;
import com.vet.hc.api.shared.domain.repository.RepositoryExceptionHandler;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public final class RepositoryErrorControllerAdvice {
    private final RepositoryExceptionHandler repositoryExceptionHandler;

    @ExceptionHandler(RepositoryException.class)
    public void handle(RepositoryException e) {
        repositoryExceptionHandler.handle(e);
    }
}
