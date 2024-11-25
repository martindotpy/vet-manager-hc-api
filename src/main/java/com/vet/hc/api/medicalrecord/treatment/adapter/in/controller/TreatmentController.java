package com.vet.hc.api.medicalrecord.treatment.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import com.vet.hc.api.medicalrecord.treatment.adapter.in.request.UpdateTreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.adapter.in.response.TreatmentResponse;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.DeleteTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.UpdateTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
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
 * Medical treatment controller.
 */
@Tag(name = "Medical treatment", description = "Medical record treatment")
@Path("/medicalrecord/treatment")
@NoArgsConstructor
public class TreatmentController {
    private UpdateTreatmentPort updateTreatmentPort;
    private DeleteTreatmentPort deleteTreatmentPort;

    @Inject
    public TreatmentController(
            UpdateTreatmentPort updateTreatmentPort,
            DeleteTreatmentPort deleteTreatmentPort) {
        this.updateTreatmentPort = updateTreatmentPort;
        this.deleteTreatmentPort = deleteTreatmentPort;
    }

    /**
     * Update a treatment.
     *
     * @param id The treatment id.
     * @return The updated treatment
     */
    @Operation(summary = "Update a treatment", description = "Update a treatment.", responses = {
            @ApiResponse(responseCode = "200", description = "Medical record was updated successfully.", content = @Content(schema = @Schema(implementation = TreatmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid treatment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medical record was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateTreatmentDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<TreatmentDto, TreatmentFailure> result = updateTreatmentPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                TreatmentResponse.class,
                result.getSuccess(),
                "Historial médico actualizado exitosamente");
    }

    /**
     * Delete a treatment.
     *
     * @param id The treatment id.
     * @return The deleted treatment
     */
    @Operation(summary = "Delete a treatment", description = "Delete a treatment.", responses = {
            @ApiResponse(responseCode = "200", description = "Treatment was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Treatment was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        Result<Void, TreatmentFailure> result = deleteTreatmentPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Historial médico eliminado exitosamente");
    }
}
