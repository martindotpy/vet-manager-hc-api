package com.vet.hc.api.bill.appointmentsale.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
import com.vet.hc.api.bill.appointmentsale.adapter.in.request.UpdateAppointmentSaleRequest;
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
import lombok.RequiredArgsConstructor;

/**
 * Appointment sale controller.
 */
@Tag(name = "Appointment sale", description = "Bill appointment sale")
@RestControllerAdapter
@RequestMapping("/appointment/sale")
@RequiredArgsConstructor
public class AppointmentSaleController {
    private final UpdateAppointmentSalePort updateAppointmentSalePort;
    private final DeleteAppointmentSalePort deleteAppointmentSalePort;

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
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateAppointmentSaleRequest request) {
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
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void, AppointmentSaleFailure> result = deleteAppointmentSalePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Venta de cita eliminada exitosamente");
    }
}
