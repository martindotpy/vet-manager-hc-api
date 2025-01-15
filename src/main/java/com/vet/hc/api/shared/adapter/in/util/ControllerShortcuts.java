package com.vet.hc.api.shared.adapter.in.util;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse.Detail;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;
import com.vet.hc.api.shared.adapter.in.status.HttpStatusCodeFailureProvider;
import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for controller shortcuts.
 */
@Slf4j
@Component
public final class ControllerShortcuts {
    /**
     * Content response with a message.
     *
     * <p>
     * Use this method to define the type of content response and validate it in
     * compile time.
     * </p>
     *
     * @param <T>             the type of the body.
     * @param <R>             the type of the content response.
     * @param contentResponse the content response class.
     * @param body            the body.
     * @param message         the message.
     * @return the response
     */
    public static <T, R extends ContentResponse<T>> ResponseEntity<?> toOkResponse(
            Class<R> contentResponse,
            T body,
            String message) {
        return toOkResponse(body, message);
    }

    /**
     * Content response with a message.
     *
     * @param body    the body.
     * @param message the message.
     * @return the response
     */
    public static ResponseEntity<?> toOkResponse(
            Object body,
            String message) {
        log.info("Creating OK response with message `{}` and body `{}`", message, body.getClass().getSimpleName());

        return ResponseEntity.ok(
                ContentResponse.builder()
                        .message(message)
                        .content(body)
                        .build());
    }

    /**
     * Basic response with a simple message.
     *
     * @param message the message.
     * @return the response
     */
    public static ResponseEntity<?> toOkResponse(String message) {
        log.info("Creating OK response with message `{}`", message);

        return ResponseEntity.ok(
                BasicResponse.builder()
                        .message(message)
                        .build());
    }

    /**
     * Paginated response with a message.
     *
     * <p>
     * Use this method to define the type of paginated content response and validate
     * it in compile time.
     * </p>
     *
     * @param <T>                      the type of the entity.
     * @param <P>                      the type of the paginated content.
     * @param <R>                      the type of the paginated content response.
     * @param paginatedContentResponse the paginated content response class.
     * @param paginated                the paginated content.
     * @param message                  the message.
     * @return the response
     */
    public static <T, P extends Paginated<T>, R extends PaginatedResponse<T>> ResponseEntity<?> toPaginatedResponse(
            Class<R> paginatedContentResponse,
            P paginated, String message) {
        return toPaginatedResponse(paginated, message);
    }

    /**
     * Basic response with a simple message.
     *
     * @param paginated the paginated content.
     * @param message   the message.
     * @return the response
     */
    public static ResponseEntity<?> toPaginatedResponse(Paginated<?> paginated, String message) {
        log.info("Creating paginated response with message `{}` with entity `{}`",
                message,
                paginated.getContent().getClass().getSimpleName());

        return ResponseEntity.ok(
                PaginatedResponse.from(paginated, message));
    }

    /**
     * Create a basic failure response.
     *
     * @param message    the message.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the message or HTTP status is null.
     * @return the response.
     */
    public static ResponseEntity<?> toFailureResponse(String message, HttpStatus httpStatus) {
        Objects.requireNonNull(message, "The message cannot be null");
        Objects.requireNonNull(httpStatus, "The HTTP status cannot be null");

        log.info("Creating failure response with message `{}` and HTTP status `{}`", message, httpStatus);

        return ResponseEntity.status(httpStatus)
                .body(FailureResponse.builder()
                        .message(message)
                        .build());
    }

    /**
     * Create a detailed basic failure response.
     *
     * @param validationErrors the validation errors.
     * @return the response.
     */
    public static ResponseEntity<?> toDetailedFailureResponse(List<ValidationError> validationErrors) {
        Objects.requireNonNull(validationErrors, "The validation errors cannot be null");

        List<Detail> details = new CopyOnWriteArrayList<>();

        for (ValidationError validationError : validationErrors) {
            Detail detail = details.stream()
                    .filter(d -> d.getField().equals(validationError.getField()))
                    .findFirst()
                    .orElseGet(() -> {
                        Detail newDetail = new Detail(validationError.getField(), new CopyOnWriteArrayList<>());
                        details.add(newDetail);
                        return newDetail;
                    });

            detail.getMessages().add(validationError.getMessage());
        }

        log.info("Creating validation failure response with validation errors `{}`",
                ansi().fgBrightRed().a(validationErrors).reset().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DetailedFailureResponse.builder()
                        .message("Errores en la consulta")
                        .details(details)
                        .build());
    }

    /**
     * Create a basic failure response.
     *
     * @param failure    the failure.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the failure or HTTP status is null.
     * @return the response.
     */
    public static ResponseEntity<?> toFailureResponse(Failure failure, HttpStatus httpStatus) {
        Objects.requireNonNull(failure, "The failure cannot be null");

        return toFailureResponse(failure.getMessage(), httpStatus);
    }

    /**
     * Create a basic failure response.
     *
     * @param failure    the failure.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the failure is null.
     * @return the response.
     */
    public static ResponseEntity<?> toFailureResponse(Failure failure, int httpStatus) {
        Objects.requireNonNull(failure, "The failure cannot be null");

        log.info("Creating failure response of `{}` and HTTP status `{}` for `{}`",
                failure,
                httpStatus,
                failure.getClass().getSimpleName());

        return ResponseEntity.status(httpStatus)
                .body(FailureResponse.builder()
                        .message(failure.getMessage())
                        .build());
    }

    /**
     * Create a basic failure response.
     *
     * @param failure the failure.
     * @throws IllegalArgumentException if the failure is null.
     * @return the response.
     */
    public static ResponseEntity<?> toFailureResponse(Failure failure) {
        return toFailureResponse(failure, HttpStatusCodeFailureProvider.get(failure));
    }

    /**
     * Create a basic failure response.
     *
     * @param file     the file.
     * @param fileName the file name.
     * @return the response.
     */
    public static ResponseEntity<byte[]> toFileResponse(byte[] file, String fileName) {
        log.info("Creating file response with file name `{}`", fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + fileName);

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
