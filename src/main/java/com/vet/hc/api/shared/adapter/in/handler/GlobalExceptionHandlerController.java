package com.vet.hc.api.shared.adapter.in.handler;

import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.domain.util.CaseConverterUtil.toSnakeCase;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.vet.hc.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vet.hc.api.shared.adapter.in.exception.InvalidEnumValueException;
import com.vet.hc.api.shared.domain.failure.GlobalFailure;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler controller.
 *
 * <p>
 * Handles global exceptions thrown by the application.
 * </p>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerController {
    @Value("${spring.profiles.active:dev}")
    private List<String> profileActive;

    /**
     * Handles exceptions that extend from {@link Exception}.
     *
     * @param e The exception thrown by the application.
     * @return Internal server error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception: ", e);

        return toFailureResponse(GlobalFailure.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions that extend from {@link ServletException}.
     *
     * @param e The exception thrown by the application.
     * @return Forbidden response
     */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<?> handleServletException(ServletException e) {
        log.error("Servlet exception: " + e.getMessage());
        log.debug("", e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Handles {@link NoResourceFoundException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Forbidden response
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException e) {
        if (e.getResourcePath().matches(".*\\.(php|env|json|py|js|txt|xml)")) {
            log.error("Redirecting to Rick Roll");

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "https://www.youtube.com/watch?v=dQw4w9WgXcQ").build();
        }

        log.error("No resource found: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Forbidden response
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("Http request method not supported: " + e.getMessage());

        if (profileActive.contains("dev"))
            return toFailureResponse(GlobalFailure.METHOD_NOT_ALLOWED);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Handles {@link UnsupportedOperationException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Forbidden response
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("Unsupported operation: ", e);

        if (profileActive.contains("dev"))
            return toFailureResponse(GlobalFailure.UNSUPPORTED_OPERATION);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Handles {@link GetUserWhenDoNotLoggedInException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Server error response
     */
    @ExceptionHandler(GetUserWhenDoNotLoggedInException.class)
    public ResponseEntity<?> handleGetUserWhenDoNotLoggedInException(GetUserWhenDoNotLoggedInException e) {
        log.error("Get user when do not logged in: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Handles {@link HttpMessageNotReadableException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Bad request response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        if (e.getMostSpecificCause() instanceof JsonParseException jsonParseException) {
            GlobalFailure failure = GlobalFailure.JSON_PARSE_ERROR;
            JsonLocation jsonLocation = jsonParseException.getLocation();

            failure.setArgs(jsonLocation.getLineNr(),
                    jsonLocation.getColumnNr(),
                    jsonParseException.getOriginalMessage());

            return toFailureResponse(failure);
        }

        else if (e.getMostSpecificCause() instanceof DateTimeParseException dateTimeParseException) {
            GlobalFailure failure = GlobalFailure.CANNOT_PARSE_DATETIME;

            failure.setArgs(dateTimeParseException.getMessage());

            return toFailureResponse(failure);
        }

        else if (e.getMostSpecificCause() instanceof InvalidTypeIdException invalidTypeIdException) {
            GlobalFailure failure = GlobalFailure.INVALID_ENUM_VALUE;

            String originalMessage = invalidTypeIdException.getOriginalMessage();
            failure.setArgs(originalMessage.substring(
                    originalMessage.indexOf("[") + 1,
                    originalMessage.indexOf("]")));

            return toFailureResponse(failure);
        }

        log.error("Http message not readable: ", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Bad request response
     */
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException e) {

        if (e.getMostSpecificCause() instanceof InvalidEnumValueException invalidEnumValueException) {
            GlobalFailure failure = GlobalFailure.INVALID_ENUM_VALUE;

            String field = e.getPropertyName();
            failure.setArgs(
                    String.join(
                            ", ",
                            Stream.of(invalidEnumValueException.getEnumType().getEnumConstants())
                                    .map(Enum::name)
                                    .map(String::toLowerCase)
                                    .toList()));

            return toDetailedFailureResponse(List.of(
                    new ValidationError(field, failure.getMessage())));
        }

        else if (e.getMostSpecificCause() instanceof IllegalArgumentException illegalArgumentException) {
            GlobalFailure failure = GlobalFailure.ILLEGAL_ARGUMENT;

            StringBuilder fieldBuilder = new StringBuilder();

            if (e.getParameter().hasParameterAnnotation(PathVariable.class)) {
                fieldBuilder.append("path.");
            }

            fieldBuilder.append(e.getPropertyName());

            failure.setArgs(illegalArgumentException.getMessage());

            return toDetailedFailureResponse(List.of(
                    new ValidationError(toSnakeCase(fieldBuilder.toString()), failure.getMessage())));
        }

        log.error("Method argument type mismatch: ", e);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    /**
     * Handles {@link AuthorizationDeniedException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Forbidden response
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
