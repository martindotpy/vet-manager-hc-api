package com.vluepixel.vetmanager.api.patient.species.adapter.in.controller;

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

import com.vluepixel.vetmanager.api.patient.species.adapter.in.response.PaginatedSpeciesResponse;
import com.vluepixel.vetmanager.api.patient.species.adapter.in.response.SpeciesResponse;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.CreateSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.DeleteSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.FindSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.application.port.in.UpdateSpeciesPort;
import com.vluepixel.vetmanager.api.patient.species.domain.request.CreateSpeciesRequest;
import com.vluepixel.vetmanager.api.patient.species.domain.request.UpdateSpeciesRequest;
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
 * Species controller.
 */
@Tag(name = "Species", description = "Species")
@RestControllerAdapter
@RequestMapping("/species")
@RequiredArgsConstructor
public final class SpeciesController {
    private final FindSpeciesPort findSpeciesPort;
    private final CreateSpeciesPort createSpeciesPort;
    private final UpdateSpeciesPort updateSpeciesPort;
    private final DeleteSpeciesPort deleteSpeciesPort;

    /**
     * Get all species by paginated criteria.
     *
     * @param page    The page number.
     * @param size    The page size.
     * @param order   The order.
     * @param orderBy The order by field.
     * @param id      The species id.
     * @param name    The species name.
     * @return Paginated response with the species found
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all species by paginated criteria")
    @GetMapping
    public ResponseEntity<PaginatedSpeciesResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name)
            throws ValidationException {
        return okPaginated(
                findSpeciesPort::findPaginatedBy,
                page,
                size,
                Order.of(order, orderBy),
                Criteria.of(
                        like("id", id),
                        like("name", name)),
                "Especies encontradas",
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Create a species.
     *
     * @param request The create species request.
     * @return Response with the created species
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a species")
    @PostMapping
    public ResponseEntity<SpeciesResponse> create(@RequestBody CreateSpeciesRequest request)
            throws ValidationException {
        return ok(() -> createSpeciesPort.create(request),
                "Especie creada exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Update a species.
     *
     * @param request The update species request.
     * @return Response with the updated species
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update a species")
    @PutMapping
    public ResponseEntity<SpeciesResponse> update(@RequestBody UpdateSpeciesRequest request)
            throws ValidationException {
        return ok(() -> updateSpeciesPort.update(request),
                "Cita actualizada exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Delete a species.
     *
     * @param id The species id.
     * @return Response with an ok message
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a species")
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id)
            throws NotFoundException {
        return ok(() -> deleteSpeciesPort.deleteById(id),
                "Cita eliminada exitosamente",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
