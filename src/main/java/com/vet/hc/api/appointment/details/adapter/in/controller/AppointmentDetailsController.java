package com.vet.hc.api.appointment.details.adapter.in.controller;

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

import com.vet.hc.api.appointment.details.adapter.in.request.UpdateAppointmentDetailsRequest;
import com.vet.hc.api.appointment.details.adapter.in.response.AppointmentDetailsResponse;
import com.vet.hc.api.appointment.details.application.port.in.DeleteAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.application.port.in.UpdateAppointmentDetailsPort;
import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.appointment.details.domain.failure.AppointmentDetailsFailure;
import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
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
 * Appointment details controller.
 */
@Tag(name = "Appointment details", description = "Appointment details")
@RestControllerAdapter
@RequestMapping("/appointment/details")
@RequiredArgsConstructor
public class AppointmentDetailsController {
    private final UpdateAppointmentDetailsPort updateAppointmentDetailsPort;
    private final DeleteAppointmentDetailsPort deleteAppointmentDetailsPort;

    /**
     * Update a appointment details.
     *
     * @param id The appointment details id.
     * @return The updated appointment details
     */
    @Operation(summary = "Update a appointment details", description = "Update a appointment details.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment details was updated successfully.", content = @Content(schema = @Schema(implementation = AppointmentDetailsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment details data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Appointment details was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateAppointmentDetailsRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<AppointmentDetailsDto, AppointmentDetailsFailure> result = updateAppointmentDetailsPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentDetailsResponse.class,
                result.getSuccess(),
                "El detalle de la cita fue actualizada exitosamente");
    }

    /**
     * Delete a appointment details.
     *
     * @param id The appointment details id.
     * @return The deleted appointment details
     */
    @Operation(summary = "Delete a appointment details", description = "Delete a appointment details.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment details was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Appointment details was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void, AppointmentDetailsFailure> result = deleteAppointmentDetailsPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("El detalle de la cita fue eliminado exitosamente");
    }
}
