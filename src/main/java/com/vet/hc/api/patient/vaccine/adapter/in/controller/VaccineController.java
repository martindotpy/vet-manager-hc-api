package com.vet.hc.api.patient.vaccine.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import com.vet.hc.api.patient.vaccine.adapter.in.request.UpdateVaccineDto;
import com.vet.hc.api.patient.vaccine.adapter.in.response.VaccineResponse;
import com.vet.hc.api.patient.vaccine.application.port.in.DeleteVaccinePort;
import com.vet.hc.api.patient.vaccine.application.port.in.UpdateVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 * Vaccine controller.
 */
@Tag(name = "Vaccine", description = "Patient vaccine")
@Path("/patient/vaccine")
@NoArgsConstructor
public class VaccineController {
    private UpdateVaccinePort updateVaccinePort;
    private DeleteVaccinePort deleteVaccinePort;

    @Inject
    public VaccineController(
            UpdateVaccinePort updateVaccinePort,
            DeleteVaccinePort deleteVaccinePort) {
        this.updateVaccinePort = updateVaccinePort;
        this.deleteVaccinePort = deleteVaccinePort;
    }

    /**
     * Update a vaccine.
     *
     * @param id The vaccine id.
     * @return The updated vaccine
     */
    @Operation(summary = "Update a vaccine", description = "Update a vaccine.", responses = {
            @ApiResponse(responseCode = "200", description = "Vaccine was updated successfully.", content = @Content(schema = @Schema(implementation = VaccineResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid vaccine data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vaccine was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateVaccineDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<VaccineDto, VaccineFailure> result = updateVaccinePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                VaccineResponse.class,
                result.getSuccess(),
                "Vacuna actualizada exitosamente");
    }

    /**
     * Delete a vaccine.
     *
     * @param id The vaccine id.
     * @return The deleted vaccine
     */
    @Operation(summary = "Delete a vaccine", description = "Delete a vaccine.", responses = {
            @ApiResponse(responseCode = "200", description = "Vaccine was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vaccine was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        Result<Void, VaccineFailure> result = deleteVaccinePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Vacuna eliminada exitosamente");
    }
}
