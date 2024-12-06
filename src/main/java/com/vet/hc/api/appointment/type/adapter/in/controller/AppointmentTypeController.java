package com.vet.hc.api.appointment.type.adapter.in.controller;

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

import com.vet.hc.api.appointment.type.adapter.in.request.CreateAppointmentTypeRequest;
import com.vet.hc.api.appointment.type.adapter.in.request.UpdateAppointmentTypeRequest;
import com.vet.hc.api.appointment.type.adapter.in.response.AppointmentTypeResponse;
import com.vet.hc.api.appointment.type.adapter.in.response.AppointmentTypesResponse;
import com.vet.hc.api.appointment.type.application.port.in.CreateAppointmentTypePort;
import com.vet.hc.api.appointment.type.application.port.in.DeleteAppointmentTypePort;
import com.vet.hc.api.appointment.type.application.port.in.FindAppointmentTypePort;
import com.vet.hc.api.appointment.type.application.port.in.UpdateAppointmentTypePort;
import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
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
 * Appointment type controller.
 */
@Tag(name = "Appointment type", description = "Appointment type")
@RestControllerAdapter
@RequestMapping("/appointment/type")
@RequiredArgsConstructor
public class AppointmentTypeController {
    private final CreateAppointmentTypePort createAppointmentTypePort;
    private final FindAppointmentTypePort findAppointmentTypePort;
    private final UpdateAppointmentTypePort updateAppointmentTypePort;
    private final DeleteAppointmentTypePort deleteAppointmentTypePort;

    /**
     * Get all appointment types.
     *
     * @return The appointment types
     */
    @Operation(summary = "Get all appointment types", description = "Get all appointment types.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment types retrieved successfully.", content = @Content(schema = @Schema(implementation = AppointmentTypesResponse.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<AppointmentTypeDto> appointmentTypes = findAppointmentTypePort.findAll();

        appointmentTypes.sort((a, b) -> a.getName().compareTo(b.getName()));

        return toOkResponse(
                AppointmentTypesResponse.class,
                appointmentTypes,
                "Tipos de citas encontradas exitosamente");
    }

    /**
     * Create a new appointment type.
     *
     * @param request The appointment type data.
     * @return The created appointment type
     */
    @Operation(summary = "Create a new appointment type", description = "Create a new appointment type.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type was created successfully.", content = @Content(schema = @Schema(implementation = AppointmentTypeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment type data.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Appointment type name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAppointmentTypeRequest request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createAppointmentTypePort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentTypeResponse.class,
                result.getSuccess(),
                "El tipo de cita fue creada exitosamente");
    }

    /**
     * Update a appointment type.
     *
     * @param id The appointment type id.
     * @return The updated appointment type
     */
    @Operation(summary = "Update a appointment type", description = "Update a appointment type.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type was updated successfully.", content = @Content(schema = @Schema(implementation = AppointmentTypeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment type data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Appointment type was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "409", description = "Appointment type name already in use.", content = @Content(schema = @Schema(implementation = FailureResponse.class)))
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateAppointmentTypeRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateAppointmentTypePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentTypeResponse.class,
                result.getSuccess(),
                "El tipo de cita fue actualizada exitosamente");
    }

    /**
     * Delete a appointment type.
     *
     * @param id The appointment type id.
     * @return The deleted appointment type
     */
    @Operation(summary = "Delete a appointment type", description = "Delete a appointment type.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Appointment type was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        var result = deleteAppointmentTypePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("El tipo de cita fue eliminado exitosamente");
    }
}
