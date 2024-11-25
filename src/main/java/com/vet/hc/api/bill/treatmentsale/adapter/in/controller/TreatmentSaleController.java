package com.vet.hc.api.bill.treatmentsale.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import com.vet.hc.api.bill.treatmentsale.adapter.in.request.UpdateTreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.adapter.in.response.TreatmentSaleResponse;
import com.vet.hc.api.bill.treatmentsale.application.port.in.DeleteTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.application.port.in.UpdateTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
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
 * Treatment sale controller.
 */
@Tag(name = "Treatment sale", description = "Bill treatment sale")
@Path("/treatment/sale")
@NoArgsConstructor
public class TreatmentSaleController {
    private UpdateTreatmentSalePort updateTreatmentSalePort;
    private DeleteTreatmentSalePort deleteTreatmentSalePort;

    @Inject
    public TreatmentSaleController(
            UpdateTreatmentSalePort updateTreatmentSalePort,
            DeleteTreatmentSalePort deleteTreatmentSalePort) {
        this.updateTreatmentSalePort = updateTreatmentSalePort;
        this.deleteTreatmentSalePort = deleteTreatmentSalePort;
    }

    /**
     * Update a treatment sale.
     *
     * @param id The treatment sale id.
     * @return The updated treatment sale
     */
    @Operation(summary = "Update a treatment sale", description = "Update a treatment sale.", responses = {
            @ApiResponse(responseCode = "200", description = "Treatment sale was updated successfully.", content = @Content(schema = @Schema(implementation = TreatmentSaleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid treatment sale data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Treatment sale was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateTreatmentSaleDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<TreatmentSaleDto, TreatmentSaleFailure> result = updateTreatmentSalePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                TreatmentSaleResponse.class,
                result.getSuccess(),
                "Venta de tratamiento actualizada exitosamente");
    }

    /**
     * Delete a treatment sale.
     *
     * @param id The treatment sale id.
     * @return The deleted treatment sale
     */
    @Operation(summary = "Delete a treatment sale", description = "Delete a treatment sale.", responses = {
            @ApiResponse(responseCode = "200", description = "Treatment sale was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Treatment sale was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        Result<Void, TreatmentSaleFailure> result = deleteTreatmentSalePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Venta de tratamiento eliminada exitosamente");
    }
}
