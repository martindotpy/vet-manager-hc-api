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
     * @return paginated response with the species found
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all species by paginated criteria", responses = {
            @ApiResponse(responseCode = "200", description = "Species found"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
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
     * Get a species by id.
     *
     * @param id The species id.
     * @return response with the species found
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the species is not found.
     */
    @Operation(summary = "Get a species by id", responses = {
            @ApiResponse(responseCode = "200", description = "Species found"),
            @ApiResponse(responseCode = "404", description = "Species not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SpeciesResponse> getById(@PathVariable Integer id)
            throws ValidationException, NotFoundException {
        return ok(() -> findSpeciesPort.findById(id),
                "Especie encontrada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Create a species.
     *
     * @param request The create species request.
     * @return response with the created species
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a species", responses = {
            @ApiResponse(responseCode = "200", description = "Species created"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
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
     * @return response with the updated species
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the species is not found.
     */
    @Operation(summary = "Update a species", responses = {
            @ApiResponse(responseCode = "200", description = "Species updated"),
            @ApiResponse(responseCode = "404", description = "Species not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping
    public ResponseEntity<SpeciesResponse> update(@RequestBody UpdateSpeciesRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> updateSpeciesPort.update(request),
                "Cita actualizada exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Delete a species.
     *
     * @param id The species id.
     * @return response with an ok message
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a species", responses = {
            @ApiResponse(responseCode = "200", description = "Species deleted"),
            @ApiResponse(responseCode = "404", description = "Species not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id)
            throws ValidationException, NotFoundException {
        return ok(() -> deleteSpeciesPort.deleteById(id),
                "Cita eliminada exitosamente",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
