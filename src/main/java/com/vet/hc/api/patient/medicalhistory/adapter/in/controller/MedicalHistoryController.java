package com.vet.hc.api.patient.medicalhistory.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import com.vet.hc.api.patient.medicalhistory.adapter.in.request.UpdateMedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.adapter.in.response.MedicalHistoryResponse;
import com.vet.hc.api.patient.medicalhistory.application.port.in.DeleteMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.application.port.in.UpdateMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
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
 * Medical history controller.
 */
@Tag(name = "Medical history", description = "Patient medical history")
@Path("/patient/medicalhistory")
@NoArgsConstructor
public class MedicalHistoryController {
    private UpdateMedicalHistoryPort updateMedicalHistoryPort;
    private DeleteMedicalHistoryPort deleteMedicalHistoryPort;

    @Inject
    public MedicalHistoryController(
            UpdateMedicalHistoryPort updateMedicalHistoryPort,
            DeleteMedicalHistoryPort deleteMedicalHistoryPort) {
        this.updateMedicalHistoryPort = updateMedicalHistoryPort;
        this.deleteMedicalHistoryPort = deleteMedicalHistoryPort;
    }

    /**
     * Update a medical history.
     *
     * @param id The medical history id.
     * @return The updated medical history
     */
    @Operation(summary = "Update a medical history", description = "Update a medical history.", responses = {
            @ApiResponse(responseCode = "200", description = "Medical history was updated successfully.", content = @Content(schema = @Schema(implementation = MedicalHistoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid medical history data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medical history was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateMedicalHistoryDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<MedicalHistoryDto, MedicalHistoryFailure> result = updateMedicalHistoryPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                MedicalHistoryResponse.class,
                result.getSuccess(),
                "Historial médico actualizado exitosamente");
    }

    /**
     * Delete a medical history.
     *
     * @param id The medical history id.
     * @return The deleted medical history
     */
    @Operation(summary = "Delete a medical history", description = "Delete a medical history.", responses = {
            @ApiResponse(responseCode = "200", description = "MedicalHistory was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "MedicalHistory was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        Result<Void, MedicalHistoryFailure> result = deleteMedicalHistoryPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Historial médico eliminado exitosamente");
    }
}
