package com.vet.hc.api.patient.race.adapter.in.controller;

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
import com.vet.hc.api.patient.race.adapter.in.request.CreateRaceRequest;
import com.vet.hc.api.patient.race.adapter.in.request.UpdateRaceRequest;
import com.vet.hc.api.patient.race.adapter.in.response.RaceResponse;
import com.vet.hc.api.patient.race.adapter.in.response.RacesResponse;
import com.vet.hc.api.patient.race.application.port.in.CreateRacePort;
import com.vet.hc.api.patient.race.application.port.in.DeleteRacePort;
import com.vet.hc.api.patient.race.application.port.in.FindRacePort;
import com.vet.hc.api.patient.race.application.port.in.UpdateRacePort;
import com.vet.hc.api.patient.race.domain.dto.RaceDto;
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
 * Race controller.
 */
@Tag(name = "Race", description = "Patient race")
@RestControllerAdapter
@RequestMapping("/patient/race")
@RequiredArgsConstructor
public class RaceController {
    private final CreateRacePort createRacePort;
    private final FindRacePort findRacePort;
    private final UpdateRacePort updateRacePort;
    private final DeleteRacePort deleteRacePort;

    /**
     * Get all races.
     *
     * @return The races
     */
    @Operation(summary = "Get all races", description = "Get all races.", responses = {
            @ApiResponse(responseCode = "200", description = "races retrieved successfully.", content = @Content(schema = @Schema(implementation = RacesResponse.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<RaceDto> races = findRacePort.findAll();

        races.sort((a, b) -> a.getName().compareTo(b.getName()));

        return toOkResponse(
                RacesResponse.class,
                races,
                "Razas encontradas exitosamente");
    }

    /**
     * Create a new race.
     *
     * @param request The race data.
     * @return The created race
     */
    @Operation(summary = "Create a new race", description = "Create a new race.", responses = {
            @ApiResponse(responseCode = "200", description = "Race was created successfully.", content = @Content(schema = @Schema(implementation = RaceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid race data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Race name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateRaceRequest request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createRacePort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                RaceResponse.class,
                result.getSuccess(),
                "Raza creada exitosamente");
    }

    /**
     * Update a race.
     *
     * @param id The race id.
     * @return The updated race
     */
    @Operation(summary = "Update a race", description = "Update a race.", responses = {
            @ApiResponse(responseCode = "200", description = "Race was updated successfully.", content = @Content(schema = @Schema(implementation = RaceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid race data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Race was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Race name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateRaceRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateRacePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                RaceResponse.class,
                result.getSuccess(),
                "Raza actualizada exitosamente");
    }

    /**
     * Delete a race.
     *
     * @param id The race id.
     * @return The deleted race
     */
    @Operation(summary = "Delete a race", description = "Delete a race.", responses = {
            @ApiResponse(responseCode = "200", description = "Race was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Race was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var result = deleteRacePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Raza eliminada exitosamente");
    }
}
