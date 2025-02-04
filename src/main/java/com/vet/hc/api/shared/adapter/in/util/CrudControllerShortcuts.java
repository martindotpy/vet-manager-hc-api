package com.vet.hc.api.shared.adapter.in.util;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.respondContentResult;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.respondVoidResult;

import java.util.function.Supplier;

import org.springframework.http.ResponseEntity;

import com.vet.hc.api.shared.adapter.in.response.ContentResponse;
import com.vet.hc.api.shared.application.port.in.CreateEntityPort;
import com.vet.hc.api.shared.application.port.in.DeleteEntityPort;
import com.vet.hc.api.shared.application.port.in.UpdateEntityPort;
import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.payload.Payload;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.shared.domain.validation.impl.InvalidStateValidation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Shortcut methods for CRUD controller.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CrudControllerShortcuts {
    /**
     * Create entity.
     *
     * @param <P>                  the type of the payload.
     * @param <F>                  the type of the failure.
     * @param <DTO>                the type of the DTO.
     * @param <PORT>               the type of the port.
     * @param request              the request.
     * @param contentResponseClass the content response class.
     * @param port                 the create port.
     * @param message              the message.
     * @return
     */
    public static <P extends Payload, DTO, PORT extends CreateEntityPort<DTO, ? extends Failure, P>> ResponseEntity<?> respondCreate(
            Class<? extends ContentResponse<DTO>> contentResponseClass,
            P request,
            PORT port,
            String message) {
        return respondContentResult(contentResponseClass, () -> port.create(request), message);
    }

    /**
     * Update entity.
     *
     * @param <P>                  the type of the payload.
     * @param <F>                  the type of the failure.
     * @param <DTO>                the type of the DTO.
     * @param <PORT>               the type of the port.
     * @param request              the request.
     * @param contentResponseClass the content response class.
     * @param port                 the update port.
     * @param message              the message.
     * @return the response entity
     */
    public static <P extends Payload, F extends Failure, DTO, PORT extends UpdateEntityPort<DTO, F, P>> ResponseEntity<?> respondUpdate(
            Class<? extends ContentResponse<DTO>> contentResponseClass,
            P request,
            PORT port,
            String message) {
        return respondContentResult(contentResponseClass, () -> port.update(request), message);
    }

    /**
     * Delete entity.
     *
     * @param <ID>    the type of the id.
     * @param <F>     the type of the failure.
     * @param <DTO>   the type of the DTO.
     * @param <PORT>  the type of the port.
     * @param id      the id.
     * @param port    the delete port.
     * @param message the message.
     * @return the response entity
     */
    public static <ID, F extends Failure, PORT extends DeleteEntityPort<F, ID>> ResponseEntity<?> respondDeleteById(
            ID id,
            PORT port,
            String message) {
        Supplier<Result<Void, F>> deleteSupplier = () -> port.deleteById(id);

        if (id instanceof Long longId) {
            return respondVoidResult(
                    deleteSupplier,
                    message,
                    new InvalidStateValidation(longId < 1, "path.id", "Id must be greater than 1"));
        }

        return respondVoidResult(deleteSupplier, message);
    }
}
