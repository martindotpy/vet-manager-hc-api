package com.vluepixel.vetmanager.api.shared.adapter.in.util;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightYellow;
import static com.vluepixel.vetmanager.api.shared.domain.util.CaseConverterUtils.toSnakeCase;
import static com.vluepixel.vetmanager.api.shared.domain.validation.Validator.validate;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse.Detail;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.ErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.query.Paginated;
import com.vluepixel.vetmanager.api.shared.domain.validation.Validation;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationError;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for controller shortcuts.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseShortcuts {
    /**
     * Content response with a message.
     *
     * @param bodySupplier the body supplier.
     * @param message      the message.
     * @return the response
     */
    public static <T, R extends ContentResponse<T>> ResponseEntity<R> ok(
            Supplier<T> bodySupplier,
            String message,
            Validation... validations) {
        validate(validations);

        T body = bodySupplier.get();

        log.info("Creating OK response with message `{}` and body {}",
                fgBrightYellow(message),
                fgBrightYellow(body));

        @SuppressWarnings("unchecked")
        R response = (R) ContentResponse.<T>builder()
                .message(message)
                .content(body)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Basic response with a simple message.
     *
     * @param message the message.
     * @return the response
     */
    public static ResponseEntity<BasicResponse> ok(
            Runnable runnable,
            String message,
            Validation... validations) {
        validate(validations);

        runnable.run();

        log.info("Creating OK response with message `{}`",
                fgBrightYellow(message));

        return ResponseEntity.ok(
                BasicResponse.builder()
                        .message(message)
                        .build());
    }

    /**
     * Basic response with a simple message.
     *
     * @param paginatedSupplier the paginated supplier.
     * @param message           the message.
     * @return the response
     */
    public static <T, R extends PaginatedResponse<T>> ResponseEntity<R> okPaginated(
            Function<PaginatedCriteria, Paginated<T>> paginatedSupplier,
            int page,
            int size,
            Order order,
            Criteria criteria,
            String message,
            Validation... validations) {
        Validation[] allValidations = new Validation[3 + (validations != null ? validations.length : 0)];

        allValidations[0] = InvalidStateValidation.of(
                order.getType() != null && order.getField() == null,
                "query.order",
                "El campo para ordenar no puede ser nulo cuando se ha definido un orden");
        allValidations[1] = InvalidStateValidation.of(
                page < 1,
                "query.page",
                "La página no puede ser menor a 1");
        allValidations[2] = InvalidStateValidation.of(
                size < 1,
                "query.size",
                "El tamaño no puede ser menor a 1");

        if (validations != null) {
            System.arraycopy(validations, 0, allValidations, 3, validations.length);
        }

        validate(allValidations);

        PaginatedCriteria paginatedCriteria = PaginatedCriteria.of(page, size, order, criteria.getFilters());
        Paginated<T> paginated = paginatedSupplier.apply(paginatedCriteria);

        log.info("Creating paginated response with message `{}` with entity {}",
                fgBrightYellow(message),
                fgBrightYellow(paginated.getContent().isEmpty()
                        ? "Empty"
                        : paginated.getContent().get(0).getClass().getSimpleName()));

        @SuppressWarnings("unchecked")
        R response = (R) PaginatedResponse.from(paginated, message);

        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<byte[]> ok(
            Consumer<OutputStream> consumer,
            String filename,
            String contentType,
            String failureMessage) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            consumer.accept(outputStream);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                    .body(outputStream.toByteArray());
        } catch (ErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error sending file");

            throw new InternalServerErrorException(e);
        }
    }

    public static ResponseEntity<FailureResponse> badRequest(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Create a basic failure response.
     *
     * @param message    the message.
     * @param httpStatus the HTTP status.
     * @throws IllegalArgumentException if the message or HTTP status is null.
     * @return the response.
     */
    public static ResponseEntity<FailureResponse> error(String message, HttpStatus httpStatus) {
        return error(message, httpStatus.value());
    }

    public static ResponseEntity<FailureResponse> error(String message, int status) {
        Objects.requireNonNull(message, "The message cannot be null");
        if (status < 400 || status >= 600) {
            throw new IllegalArgumentException("The status must be a valid HTTP status code");
        }

        log.info("Creating failure response with message `{}` and HTTP status {}",
                fgBrightRed(message),
                fgBrightRed(status));

        return ResponseEntity.status(status)
                .body(FailureResponse.builder()
                        .message(message)
                        .build());
    }

    public static ResponseEntity<FailureResponse> error(ErrorException exception) {
        Objects.requireNonNull(exception, "The exception cannot be null");

        log.error("Creating error response for {}",
                fgBrightRed(exception.getClass().getName()));

        return error(exception.getMessage(), exception.getStatus());
    }

    /**
     * Create a detailed basic failure response.
     *
     * @param message          the message.
     * @param validationErrors the validation errors.
     * @return the response.
     */
    public static ResponseEntity<DetailedFailureResponse> validationError(
            String message,
            List<ValidationError> validationErrors) {
        Objects.requireNonNull(message, "The message cannot be null");
        Objects.requireNonNull(validationErrors, "The validation errors cannot be null");

        List<Detail> details = new CopyOnWriteArrayList<>();

        for (ValidationError validationError : validationErrors) {
            String fieldSnakeCase = toSnakeCase(validationError.getField());

            Detail detail = details.stream()
                    .filter(d -> d.getField().equals(fieldSnakeCase))
                    .findFirst()
                    .orElseGet(() -> {
                        Detail newDetail = new Detail(fieldSnakeCase, new CopyOnWriteArrayList<>());
                        details.add(newDetail);
                        return newDetail;
                    });

            detail.getMessages().add(validationError.getMessage());
        }

        log.info("Creating validation failure response with validation errors {}",
                fgBrightRed(validationErrors));

        return ResponseEntity.status(422)
                .body(DetailedFailureResponse.builder()
                        .message(message)
                        .details(details)
                        .build());
    }

    /**
     * Create a detailed basic failure response.
     *
     * @param validationErrors the validation errors.
     * @return the response.
     */
    public static ResponseEntity<DetailedFailureResponse> validationError(
            List<ValidationError> validationErrors) {
        return validationError("Validation failed", validationErrors);
    }

    public static ResponseEntity<FailureResponse> forbidden(String message) {
        return error(message, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<Void> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public static ResponseEntity<FailureResponse> methodNotAllowed() {
        return error("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    public static ResponseEntity<FailureResponse> internalServerError() {
        return internalServerError("Internal server error");
    }

    public static ResponseEntity<FailureResponse> internalServerError(Exception e) {
        log.error("Internal server error", e);

        return internalServerError("Internal server error");
    }

    public static ResponseEntity<FailureResponse> internalServerError(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<FailureResponse> notImplemented() {
        return error("Unsupported operation", HttpStatus.NOT_IMPLEMENTED);
    }
}
