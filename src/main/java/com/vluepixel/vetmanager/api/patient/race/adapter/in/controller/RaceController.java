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
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
     * @param page        The page number.
     * @param size        The page size.
     * @param order       The order.
     * @param orderBy     The order by field.
     * @param id          The race id.
     * @param name        The race name.
     * @param speciesName The species name.
     * @return paginated response with the races found
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all race by paginated criteria", responses = {
            @ApiResponse(responseCode = "200", description = "Races found"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
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
                findRacePort::findPaginatedBy,
                page,
                size,
                Order.of(order, orderBy),
                Criteria.of(
                        like("id", id),
                        like("name", name),
                        like("species.name", speciesName)),
                "Razas encontradas exitosamente",
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Get an race by id.
     *
     * @param id The race id.
     * @return response with the race found
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the race is not found.
     */
    @Operation(summary = "Get a race by id", responses = {
            @ApiResponse(responseCode = "200", description = "Race found"),
            @ApiResponse(responseCode = "404", description = "Race not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RaceResponse> getById(@PathVariable Integer id)
            throws ValidationException, NotFoundException {
        return ok(() -> findRacePort.findById(id),
                "Raza encontrada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Create a race.
     *
     * @param request The create race request.
     * @return response with the created race
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a race", responses = {
            @ApiResponse(responseCode = "200", description = "Race created"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
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
     * @return response with the updated race
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the race is not found.
     */
    @Operation(summary = "Update a race", responses = {
            @ApiResponse(responseCode = "200", description = "Race updated"),
            @ApiResponse(responseCode = "404", description = "Race not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
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
     * @return response with an ok message
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the race is not found.
     */
    @Operation(summary = "Delete a race", responses = {
            @ApiResponse(responseCode = "200", description = "Race deleted"),
            @ApiResponse(responseCode = "404", description = "Race not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id)
            throws NotFoundException {
        return ok(() -> deleteRacePort.deleteById(id),
                "Raza eliminada exitosamente",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
