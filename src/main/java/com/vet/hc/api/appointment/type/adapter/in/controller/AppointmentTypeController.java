package com.vet.hc.api.appointment.type.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.util.List;

import com.vet.hc.api.appointment.type.adapter.in.request.CreateAppointmentTypeDto;
import com.vet.hc.api.appointment.type.adapter.in.request.UpdateAppointmentTypeDto;
import com.vet.hc.api.appointment.type.adapter.in.response.AppointmentTypeResponse;
import com.vet.hc.api.appointment.type.adapter.in.response.AppointmentTypesResponse;
import com.vet.hc.api.appointment.type.application.port.in.CreateAppointmentTypePort;
import com.vet.hc.api.appointment.type.application.port.in.DeleteAppointmentTypePort;
import com.vet.hc.api.appointment.type.application.port.in.FindAppointmentTypePort;
import com.vet.hc.api.appointment.type.application.port.in.UpdateAppointmentTypePort;
import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
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
 * Appointment type controller.
 */
@Tag(name = "Appointment")
@Path("/appointment/type")
@NoArgsConstructor
public class AppointmentTypeController {
    private CreateAppointmentTypePort createAppointmentTypePort;
    private FindAppointmentTypePort findAppointmentTypePort;
    private UpdateAppointmentTypePort updateAppointmentTypePort;
    private DeleteAppointmentTypePort deleteAppointmentTypePort;

    @Inject
    public AppointmentTypeController(
            CreateAppointmentTypePort createAppointmentTypePort,
            FindAppointmentTypePort findAppointmentTypePort,
            UpdateAppointmentTypePort updateAppointmentTypePort,
            DeleteAppointmentTypePort deleteAppointmentTypePort) {
        this.createAppointmentTypePort = createAppointmentTypePort;
        this.findAppointmentTypePort = findAppointmentTypePort;
        this.updateAppointmentTypePort = updateAppointmentTypePort;
        this.deleteAppointmentTypePort = deleteAppointmentTypePort;
    }

    /**
     * Get all appointment types.
     *
     * @return The appointment types
     */
    @Operation(summary = "Get all appointment types", description = "Get all appointment types.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment types retrieved successfully.", content = @Content(schema = @Schema(implementation = AppointmentTypesResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
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
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateAppointmentTypeDto request) {
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
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateAppointmentTypeDto request) {
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
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        var result = deleteAppointmentTypePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("El tipo de cita fue eliminado exitosamente");
    }
}
