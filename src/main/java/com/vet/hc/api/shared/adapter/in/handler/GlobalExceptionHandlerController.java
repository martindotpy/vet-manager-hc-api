
package com.vet.hc.api.shared.adapter.in.handler;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vet.hc.api.shared.domain.failure.GlobalFailure;

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
    /**
     * Handles exceptions that extend from {@link ServletException}.
     *
     * @param e The exception thrown by the application
     * @return A redirection to the error page (temporary solution)
     */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<?> handleServletException(ServletException e) {
        log.error("Servlet exception: " + e.getMessage());

        return toFailureResponse(GlobalFailure.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException} exceptions.
     *
     * @param e The exception thrown by the application
     * @return A redirection to the error page (temporary solution)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Http request method not supported: " + e.getMessage());

        return toFailureResponse(GlobalFailure.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles {@link UnsupportedOperationException} exceptions.
     *
     * @param e The exception thrown by the application
     * @return A redirection to the error page (temporary solution)
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("Unsupported operation: ", e);

        return toFailureResponse(GlobalFailure.UNSUPPORTED_OPERATION);
    }
}
