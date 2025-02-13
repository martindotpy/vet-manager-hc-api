package com.vluepixel.vetmanager.api.shared.adapter.in.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vluepixel.vetmanager.api.shared.domain.exception.RepositoryException;
import com.vluepixel.vetmanager.api.shared.domain.repository.RepositoryExceptionHandler;

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
