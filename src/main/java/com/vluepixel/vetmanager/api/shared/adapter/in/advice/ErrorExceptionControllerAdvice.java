package com.vluepixel.vetmanager.api.shared.adapter.in.advice;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.error;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.validationError;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.domain.exception.ErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * Error exception controller advice.
 */
@Slf4j
@RestControllerAdvice
public final class ErrorExceptionControllerAdvice {
    /**
     * Handle error exception.
     *
     * @param e The exception.
     * @return Response with a failure message
     */
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<FailureResponse> handle(final ErrorException e) {
        return error(e);
    }

    /**
     * Handle validation exception.
     *
     * @param e The exception.
     * @return Response with a detailed failure message
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DetailedFailureResponse> handle(final ValidationException e) {
        return validationError(e.getErrors());
    }

    /**
     * Handle internal server error exception.
     *
     * @param e The exception.
     * @return Response with a failure message
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<FailureResponse> handle(final InternalServerErrorException e) {
        log.error("", e.getCause());

        return error(e);
    }

    /**
     * Handle not found exception.
     *
     * @param e The exception.
     * @return Response with a failure message
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FailureResponse> handle(final NotFoundException e) {
        return error(e);
    }
}
