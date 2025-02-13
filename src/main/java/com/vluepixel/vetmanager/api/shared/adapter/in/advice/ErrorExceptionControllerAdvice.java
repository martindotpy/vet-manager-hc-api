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
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public final class ErrorExceptionControllerAdvice {
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<FailureResponse> handle(final ErrorException exception) {
        return error(exception);
    }

    @ExceptionHandler(ValidationException.class)
    @ApiResponse(responseCode = "422", description = "Validation error")
    public ResponseEntity<DetailedFailureResponse> handle(final ValidationException exception) {
        return validationError(exception.getErrors());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<FailureResponse> handle(final InternalServerErrorException exception) {
        log.error("", exception.getCause());

        return error(exception);
    }
}
