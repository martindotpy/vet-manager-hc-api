package com.vluepixel.vetmanager.api.patient.race.adapter.in.controller;

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

import com.vluepixel.vetmanager.api.patient.race.adapter.in.response.PaginatedRaceResponse;
import com.vluepixel.vetmanager.api.patient.race.adapter.in.response.RaceResponse;
import com.vluepixel.vetmanager.api.patient.race.application.port.in.CreateRacePort;
import com.vluepixel.vetmanager.api.patient.race.application.port.in.DeleteRacePort;
import com.vluepixel.vetmanager.api.patient.race.application.port.in.FindRacePort;
import com.vluepixel.vetmanager.api.patient.race.application.port.in.UpdateRacePort;
import com.vluepixel.vetmanager.api.patient.race.domain.request.CreateRaceRequest;
import com.vluepixel.vetmanager.api.patient.race.domain.request.UpdateRaceRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Race controller.
 */
@Tag(name = "Race", description = "Race")
@RestControllerAdapter
@RequestMapping("/race")
@RequiredArgsConstructor
public final class RaceController {
    private final FindRacePort findRacePort;
    private final CreateRacePort createRacePort;
    private final UpdateRacePort updateRacePort;
    private final DeleteRacePort deleteRacePort;

    /**
     * Get all race by paginated criteria.
     *
     * @param page    The page number.
     * @param size    The page size.
     * @param order   The order.
     * @param orderBy The order by field.
     * @param id      The race id.
     * @param name    The race name.
     * @return The paginated race response
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all race by paginated criteria")
    @GetMapping
    public ResponseEntity<PaginatedRaceResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, name = "species_name") String speciesName)
            throws ValidationException {
        return okPaginated(
                () -> findRacePort.findPaginatedBy(
                        PaginatedCriteria.of(
                                page,
                                size,
                                Order.of(order, orderBy),
                                like("id", id),
                                like("name", name),
                                like("species.name", speciesName))),
                "Razas encontradas exitosamente",
                InvalidStateValidation.of(
                        order != null && orderBy == null,
                        "query.order",
                        "El campo para ordenar no puede ser nulo cuando se ha definido un orden"),
                InvalidStateValidation.of(
                        page < 1,
                        "query.page",
                        "La página no puede ser menor a 1"),
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"),
                InvalidStateValidation.of(
                        size < 1,
                        "query.size",
                        "El tamaño no puede ser menor a 1"));
    }

    /**
     * Create a race.
     *
     * @param request The create race request.
     * @return The race response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a race")
    @PostMapping
    public ResponseEntity<RaceResponse> create(@RequestBody CreateRaceRequest request)
            throws ValidationException {
        return ok(() -> createRacePort.create(request),
                "Raza creada exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Update a race.
     *
     * @param request The update race request.
     * @return The race response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update a race")
    @PutMapping
    public ResponseEntity<RaceResponse> update(@RequestBody UpdateRaceRequest request)
            throws ValidationException {
        return ok(() -> updateRacePort.update(request),
                "Raza actualizada exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Delete a race.
     *
     * @param id The race id.
     * @return The race response.
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a race")
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id)
            throws NotFoundException {
        return ok(() -> deleteRacePort.deleteById(id),
                "Raza eliminada exitosamente",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }
}
