package com.vet.hc.api.patient.race.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.List;

import com.vet.hc.api.patient.race.adapter.in.request.CreateRaceDto;
import com.vet.hc.api.patient.race.adapter.in.request.UpdateRaceDto;
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
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 * Race controller.
 */
@Tag(name = "Race", description = "Patient race")
@Path("/patient/race")
@NoArgsConstructor
public class RaceController {
    private CreateRacePort createRacePort;
    private FindRacePort findRacePort;
    private UpdateRacePort updateRacePort;
    private DeleteRacePort deleteRacePort;

    @Inject
    public RaceController(
            CreateRacePort createRacePort,
            FindRacePort findRacePort,
            UpdateRacePort updateRacePort,
            DeleteRacePort deleteRacePort) {
        this.createRacePort = createRacePort;
        this.findRacePort = findRacePort;
        this.updateRacePort = updateRacePort;
        this.deleteRacePort = deleteRacePort;
    }

    /**
     * Get all races.
     *
     * @return The races
     */
    @Operation(summary = "Get all races", description = "Get all races.", responses = {
            @ApiResponse(responseCode = "200", description = "races retrieved successfully.", content = @Content(schema = @Schema(implementation = RacesResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
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
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateRaceDto request) {
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
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateRaceDto request) {
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
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        var result = deleteRacePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Raza eliminada exitosamente");
    }
}
