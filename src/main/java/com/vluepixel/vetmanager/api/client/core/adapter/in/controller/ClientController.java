package com.vluepixel.vetmanager.api.client.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.okPaginated;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vluepixel.vetmanager.api.client.core.adapter.in.response.ClientResponse;
import com.vluepixel.vetmanager.api.client.core.adapter.in.response.PaginatedClientResponse;
import com.vluepixel.vetmanager.api.client.core.application.port.in.CreateClientPort;
import com.vluepixel.vetmanager.api.client.core.application.port.in.DeleteClientPort;
import com.vluepixel.vetmanager.api.client.core.application.port.in.FindClientPort;
import com.vluepixel.vetmanager.api.client.core.application.port.in.UpdateClientPort;
import com.vluepixel.vetmanager.api.client.core.domain.enums.IdentificationType;
import com.vluepixel.vetmanager.api.client.core.domain.request.CreateClientRequest;
import com.vluepixel.vetmanager.api.client.core.domain.request.UpdateClientRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Client controller.
 */
@Tag(name = "Client", description = "Client")
@RestControllerAdapter
@RequestMapping("/client")
@RequiredArgsConstructor
public final class ClientController {
    private final FindClientPort findClientPort;
    private final CreateClientPort createClientPort;
    private final UpdateClientPort updateClientPort;
    private final DeleteClientPort deleteClientPort;

    /**
     * Get all client by paginated criteria.
     *
     * @param page               The page number.
     * @param size               The page size.
     * @param order              The order.
     * @param orderBy            The order by field.
     * @param id                 The client id.
     * @param firstName          The client first name.
     * @param lastName           The client last name.
     * @param identification     The client identification.
     * @param identificationType The client identification type.
     * @param address            The client address.
     * @param phone              The client phone.
     * @param email              The client email.
     * @return Paginated response with the clients found
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all client by paginated criteria")
    @GetMapping
    public ResponseEntity<PaginatedClientResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false, name = "first_name") String firstName,
            @RequestParam(required = false, name = "last_name") String lastName,
            @RequestParam(required = false) String identification,
            @RequestParam(required = false, name = "identification_type") IdentificationType identificationType,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email)
            throws ValidationException {
        return okPaginated(
                findClientPort::findPaginatedBy,
                page,
                size,
                Order.of(order, orderBy),
                Criteria.of(
                        like("id", id),
                        like("firstName", firstName),
                        like("lastName", lastName),
                        like("identification", identification),
                        like("identificationType", identificationType),
                        like("address", address),
                        like("phones", phone),
                        like("emails", email)),
                "Tipos de cliente encontradas",
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Create a client.
     *
     * @param request The create client request.
     * @return Response with the created client
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a client")
    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody CreateClientRequest request)
            throws ValidationException {
        return ok(() -> createClientPort.create(request),
                "Cliente creado",
                ValidationRequest.of(request));
    }

    /**
     * Update a client.
     *
     * @param request The update client request.
     * @return Response with the updated client
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update a client")
    @PutMapping
    public ResponseEntity<ClientResponse> update(@RequestBody UpdateClientRequest request)
            throws ValidationException {
        return ok(() -> updateClientPort.update(request),
                "Cliente actualizado",
                ValidationRequest.of(request));
    }

    /**
     * Delete a client.
     *
     * @param id The client id.
     * @return Response with an ok message
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a client")
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long id)
            throws NotFoundException {
        return ok(() -> deleteClientPort.deleteById(id),
                "Cliente eliminado",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
