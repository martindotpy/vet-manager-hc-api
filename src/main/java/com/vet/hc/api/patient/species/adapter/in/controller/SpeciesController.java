package com.vet.hc.api.patient.species.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
import com.vet.hc.api.patient.species.adapter.in.request.CreateSpeciesRequest;
import com.vet.hc.api.patient.species.adapter.in.request.UpdateSpeciesRequest;
import com.vet.hc.api.patient.species.adapter.in.response.DifferentSpeciesResponse;
import com.vet.hc.api.patient.species.adapter.in.response.SpeciesResponse;
import com.vet.hc.api.patient.species.application.port.in.CreateSpeciesPort;
import com.vet.hc.api.patient.species.application.port.in.DeleteSpeciesPort;
import com.vet.hc.api.patient.species.application.port.in.FindSpeciesPort;
import com.vet.hc.api.patient.species.application.port.in.UpdateSpeciesPort;
import com.vet.hc.api.patient.species.domain.dto.SpeciesDto;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Species controller.
 */
@Tag(name = "Species", description = "Patient species")
@RestControllerAdapter
@RequestMapping("/patient/species")
@RequiredArgsConstructor
public class SpeciesController {
    private final CreateSpeciesPort createSpeciesPort;
    private final FindSpeciesPort findSpeciesPort;
    private final UpdateSpeciesPort updateSpeciesPort;
    private final DeleteSpeciesPort deleteSpeciesPort;

    /**
     * Get all species.
     *
     * @return The species
     */
    @Operation(summary = "Get all species", description = "Get all species.", responses = {
            @ApiResponse(responseCode = "200", description = "species retrieved successfully.", content = @Content(schema = @Schema(implementation = DifferentSpeciesResponse.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SpeciesDto> species = findSpeciesPort.findAll();

        species.sort((a, b) -> a.getName().compareTo(b.getName()));

        return toOkResponse(
                DifferentSpeciesResponse.class,
                species,
                "Especies encontradas exitosamente");
    }

    /**
     * Create a new species.
     *
     * @param request The species data.
     * @return The created species
     */
    @Operation(summary = "Create a new species", description = "Create a new species.", responses = {
            @ApiResponse(responseCode = "200", description = "Species was created successfully.", content = @Content(schema = @Schema(implementation = SpeciesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid species data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Species name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateSpeciesRequest request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createSpeciesPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                SpeciesResponse.class,
                result.getSuccess(),
                "Especies creada exitosamente");
    }

    /**
     * Update a species.
     *
     * @param id The species id.
     * @return The updated species
     */
    @Operation(summary = "Update a species", description = "Update a species.", responses = {
            @ApiResponse(responseCode = "200", description = "Species was updated successfully.", content = @Content(schema = @Schema(implementation = SpeciesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid species data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Species was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Species name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateSpeciesRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateSpeciesPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                SpeciesResponse.class,
                result.getSuccess(),
                "Especies actualizada exitosamente");
    }

    /**
     * Delete a species.
     *
     * @param id The species id.
     * @return The deleted species
     */
    @Operation(summary = "Delete a species", description = "Delete a species.", responses = {
            @ApiResponse(responseCode = "200", description = "Species was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Species was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var result = deleteSpeciesPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Especies eliminada exitosamente");
    }
}
