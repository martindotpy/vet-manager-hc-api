package com.vet.hc.api.shared.adapter.in.advice;

import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.error;
import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.validationError;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.exception.ErrorException;
import com.vet.hc.api.shared.domain.exception.InternalServerErrorException;
import com.vet.hc.api.shared.domain.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public final class ErrorExceptionControllerAdvice {
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<FailureResponse> handle(final ErrorException exception) {
        return error(exception);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DetailedFailureResponse> handle(final ValidationException exception) {
        return validationError(exception.getErrors());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<FailureResponse> handle(final InternalServerErrorException exception) {
        log.error("", exception.getCause());

        return error(exception);
    }
}
