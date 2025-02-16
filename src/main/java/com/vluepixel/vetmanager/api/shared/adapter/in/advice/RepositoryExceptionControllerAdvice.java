package com.vluepixel.vetmanager.api.shared.adapter.in.advice;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.internalServerError;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vluepixel.vetmanager.api.shared.domain.exception.ErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.RepositoryException;
import com.vluepixel.vetmanager.api.shared.domain.repository.RepositoryExceptionHandler;

import lombok.RequiredArgsConstructor;

/**
 * Repository exception controller advice.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public final class RepositoryExceptionControllerAdvice {
    private final RepositoryExceptionHandler repositoryExceptionHandler;
    private final ErrorExceptionControllerAdvice errorExceptionControllerAdvice;

    /**
     * Handle repository exception.
     *
     * @param e The exception.
     * @return Response with a failure message
     */
    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<?> handle(RepositoryException e) {
        try {
            repositoryExceptionHandler.handle(e);

            return internalServerError();
        } catch (ErrorException e1) {
            return errorExceptionControllerAdvice.handle(e1);
        } catch (Exception e1) {
            return internalServerError();
        }
    }
}
