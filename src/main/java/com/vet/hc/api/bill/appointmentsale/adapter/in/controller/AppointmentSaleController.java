package com.vet.hc.api.bill.appointmentsale.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import com.vet.hc.api.bill.appointmentsale.adapter.in.request.UpdateAppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.adapter.in.response.AppointmentSaleResponse;
import com.vet.hc.api.bill.appointmentsale.application.port.in.DeleteAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.application.port.in.UpdateAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
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
 * Appointment sale controller.
 */
@Tag(name = "Appointment sale", description = "Bill appointment sale")
@Path("/appointment/sale")
@NoArgsConstructor
public class AppointmentSaleController {
    private UpdateAppointmentSalePort updateAppointmentSalePort;
    private DeleteAppointmentSalePort deleteAppointmentSalePort;

    @Inject
    public AppointmentSaleController(
            UpdateAppointmentSalePort updateAppointmentSalePort,
            DeleteAppointmentSalePort deleteAppointmentSalePort) {
        this.updateAppointmentSalePort = updateAppointmentSalePort;
        this.deleteAppointmentSalePort = deleteAppointmentSalePort;
    }

    /**
     * Update a appointment sale.
     *
     * @param id The appointment sale id.
     * @return The updated appointment sale
     */
    @Operation(summary = "Update a appointment sale", description = "Update a appointment sale.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment sale was updated successfully.", content = @Content(schema = @Schema(implementation = AppointmentSaleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment sale data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Appointment sale was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateAppointmentSaleDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<AppointmentSaleDto, AppointmentSaleFailure> result = updateAppointmentSalePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentSaleResponse.class,
                result.getSuccess(),
                "Venta de cita actualizada exitosamente");
    }

    /**
     * Delete a appointment sale.
     *
     * @param id The appointment sale id.
     * @return The deleted appointment sale
     */
    @Operation(summary = "Delete a appointment sale", description = "Delete a appointment sale.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment sale was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Appointment sale was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        Result<Void, AppointmentSaleFailure> result = deleteAppointmentSalePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Venta de cita eliminada exitosamente");
    }
}
