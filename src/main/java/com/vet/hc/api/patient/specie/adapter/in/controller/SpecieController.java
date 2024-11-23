package com.vet.hc.api.patient.specie.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.List;

import com.vet.hc.api.patient.specie.adapter.in.request.CreateSpecieDto;
import com.vet.hc.api.patient.specie.adapter.in.request.UpdateSpecieDto;
import com.vet.hc.api.patient.specie.application.port.in.CreateSpeciePort;
import com.vet.hc.api.patient.specie.application.port.in.DeleteSpeciePort;
import com.vet.hc.api.patient.specie.application.port.in.FindSpeciePort;
import com.vet.hc.api.patient.specie.application.port.in.UpdateSpeciePort;
import com.vet.hc.api.patient.specie.application.response.SpecieResponse;
import com.vet.hc.api.patient.specie.application.response.SpeciesResponse;
import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
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
 * Specie controller.
 */
@Tag(name = "Specie", description = "Veterinary species (pets)")
@Path("/specie")
@NoArgsConstructor
public class SpecieController {
    private CreateSpeciePort createSpeciePort;
    private FindSpeciePort findSpeciePort;
    private UpdateSpeciePort updateSpeciePort;
    private DeleteSpeciePort deleteSpeciePort;

    @Inject
    public SpecieController(
            CreateSpeciePort createSpeciePort,
            FindSpeciePort findSpeciePort,
            UpdateSpeciePort updateSpeciePort,
            DeleteSpeciePort deleteSpeciePort) {
        this.createSpeciePort = createSpeciePort;
        this.findSpeciePort = findSpeciePort;
        this.updateSpeciePort = updateSpeciePort;
        this.deleteSpeciePort = deleteSpeciePort;
    }

    /**
     * Get all species.
     *
     * @return The species
     */
    @Operation(summary = "Get all species", description = "Get all species using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Species retrieved successfully.", content = @Content(schema = @Schema(implementation = SpeciesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<SpecieDto> species = findSpeciePort.findAll();

        return toOkResponse(
                SpeciesResponse.class,
                species,
                "Especies encontradas exitosamente");
    }

    /**
     * Get specie by id.
     */
    @Operation(summary = "Get specie by id", description = "Get specie by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Specie retrieved successfully.", content = @Content(schema = @Schema(implementation = SpecieResponse.class))),
            @ApiResponse(responseCode = "404", description = "The specie was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        var result = findSpeciePort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                SpecieResponse.class,
                result.getSuccess(),
                "Especie encontrada exitosamente");
    }

    /**
     * Create a new specie.
     *
     * @param request The specie data.
     * @return The created specie
     */
    @Operation(summary = "Create a new specie", description = "Create a new specie.", responses = {
            @ApiResponse(responseCode = "200", description = "The specie was created successfully.", content = @Content(schema = @Schema(implementation = SpecieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid specie data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateSpecieDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createSpeciePort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                SpecieResponse.class,
                result.getSuccess(),
                "Especie creada exitosamente");
    }

    /**
     * Update a specie.
     *
     * @param id The specie id.
     * @return The updated specie
     */
    @Operation(summary = "Update a specie", description = "Update a specie.", responses = {
            @ApiResponse(responseCode = "200", description = "The specie was updated successfully.", content = @Content(schema = @Schema(implementation = SpecieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid specie data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The specie was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateSpecieDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateSpeciePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                SpecieResponse.class,
                result.getSuccess(),
                "Especie actualizada exitosamente");
    }

    /**
     * Delete a specie.
     *
     * @param id The specie id.
     * @return The deleted specie
     */
    @Operation(summary = "Delete a specie", description = "Delete a specie.", responses = {
            @ApiResponse(responseCode = "200", description = "The specie was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The specie was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSpecie(@PathParam("id") Long id) {
        var result = deleteSpeciePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Especie eliminada exitosamente");
    }
}
