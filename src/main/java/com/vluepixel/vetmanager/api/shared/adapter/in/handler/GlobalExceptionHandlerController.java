package com.vluepixel.vetmanager.api.shared.adapter.in.handler;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.badRequest;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.forbidden;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.internalServerError;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.methodNotAllowed;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.notImplemented;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.validationError;
import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toSnakeCase;

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
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.vluepixel.vetmanager.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.vluepixel.vetmanager.api.shared.adapter.in.exception.InvalidEnumValueException;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;

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

        return internalServerError();
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

        return forbidden();
    }

    /**
     * Handles {@link NoResourceFoundException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Forbidden response
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("No resource found: " + e.getMessage());

        return forbidden();
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
            return methodNotAllowed();

        return forbidden();
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
            return notImplemented();

        return forbidden();
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
            JsonLocation jsonLocation = jsonParseException.getLocation();

            return badRequest("JSON parse error at row " + jsonLocation.getLineNr() +
                    ", column " + jsonLocation.getColumnNr() + ": " + jsonParseException.getOriginalMessage());
        }

        else if (e.getMostSpecificCause() instanceof DateTimeParseException dateTimeParseException) {
            return badRequest("Cannot parse datetime: " + dateTimeParseException.getMessage());
        }

        else if (e.getMostSpecificCause() instanceof InvalidTypeIdException invalidTypeIdException) {
            String originalMessage = invalidTypeIdException.getOriginalMessage();
            String information = originalMessage.substring(
                    originalMessage.indexOf("[") + 1,
                    originalMessage.indexOf("]"));

            return badRequest("Only the following values are allowed: " + information);
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

            String field = e.getPropertyName();
            String message = String.join(
                    ", ",
                    Stream.of(invalidEnumValueException.getEnumType().getEnumConstants())
                            .map(Enum::name)
                            .map(String::toLowerCase)
                            .toList());

            return validationError(List.of(new ValidationError(field, message)));
        }

        else if (e.getMostSpecificCause() instanceof IllegalArgumentException illegalArgumentException) {
            StringBuilder fieldBuilder = new StringBuilder();

            if (e.getParameter().hasParameterAnnotation(PathVariable.class)) {
                fieldBuilder.append("path.");
            }

            fieldBuilder.append(e.getPropertyName());

            return validationError(List.of(
                    new ValidationError(toSnakeCase(fieldBuilder.toString()),
                            "Illegal argument: " + illegalArgumentException.getMessage())));
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
        return forbidden("Acceso denegado");
    }

    /**
     * Handles {@link MissingServletRequestPartException} exceptions.
     *
     * @param e The exception thrown by the application.
     * @return Bad request response
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<DetailedFailureResponse> handle(final MissingServletRequestPartException e) {
        String name = e.getRequestPartName();

        if ("imageFile".equals(name)) {
            name = "Imagen";
        }

        return validationError(
                List.of(new ValidationError("param." + e.getRequestPartName(), name + " es requerido(a)")));
    }
}
