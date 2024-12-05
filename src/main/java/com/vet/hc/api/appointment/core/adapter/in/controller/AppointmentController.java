package com.vet.hc.api.appointment.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toFileResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toOkResponse;
import static com.vet.hc.api.shared.adapter.in.util.ResponseUtils.toPaginatedResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vet.hc.api.appointment.core.adapter.in.request.CreateAppointmentDto;
import com.vet.hc.api.appointment.core.adapter.in.request.UpdateAppointmentDto;
import com.vet.hc.api.appointment.core.application.port.in.AddDetailsToAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.CreateAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.DeleteAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.GenerateAppointmentExcelPort;
import com.vet.hc.api.appointment.core.application.port.in.UpdateAppointmentPort;
import com.vet.hc.api.appointment.core.application.response.AppointmentResponse;
import com.vet.hc.api.appointment.core.application.response.PaginatedAppointmentResponse;
import com.vet.hc.api.appointment.details.adapter.in.request.CreateAppointmentDetailsDto;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.application.util.EnumUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.FilterOperator;
import com.vet.hc.api.shared.domain.criteria.Order;
import com.vet.hc.api.shared.domain.criteria.OrderType;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;
import com.vet.hc.api.shared.domain.validation.ValidationError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.NoArgsConstructor;

/**
 * Appointment controller.
 */
@Tag(name = "Appointment", description = "Veterinary appointment")
@Path("/appointment")
@NoArgsConstructor
public class AppointmentController {
    private CreateAppointmentPort createAppointmentPort;
    private AddDetailsToAppointmentPort addDetailsToAppointmentPort;
    private FindAppointmentPort loadAppointmentPort;
    private UpdateAppointmentPort updateAppointmentPort;
    private DeleteAppointmentPort deleteAppointmentPort;
    private GenerateAppointmentExcelPort generateAppointmentExcelPort;

    @Inject
    public AppointmentController(
            CreateAppointmentPort createAppointmentPort,
            AddDetailsToAppointmentPort addDetailsToAppointmentPort,
            FindAppointmentPort loadAppointmentPort,
            UpdateAppointmentPort updateAppointmentPort,
            DeleteAppointmentPort deleteAppointmentPort,
            GenerateAppointmentExcelPort generateAppointmentExcelPort) {
        this.createAppointmentPort = createAppointmentPort;
        this.addDetailsToAppointmentPort = addDetailsToAppointmentPort;
        this.loadAppointmentPort = loadAppointmentPort;
        this.updateAppointmentPort = updateAppointmentPort;
        this.deleteAppointmentPort = deleteAppointmentPort;
        this.generateAppointmentExcelPort = generateAppointmentExcelPort;
    }

    /**
     * Get all appointments paginated.
     *
     * @param page Page number.
     * @param size Page size.
     * @return The appointments paginated
     */
    @Operation(summary = "Get all appointments paginated", description = "Get all appointments using pages.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully.", content = @Content(schema = @Schema(implementation = PaginatedAppointmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByCriteria(
            @QueryParam("page") @Parameter(required = true, description = "Page number") Integer page,
            @QueryParam("size") @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @QueryParam("order_by") @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @QueryParam("order") @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @QueryParam("patient_name") @Parameter(description = "Patient name") String patientName,
            @QueryParam("patient_id") @Parameter(description = "Patient id") String patientId,
            @QueryParam("vet_name") @Parameter(description = "Vet name") String vetName,
            @QueryParam("vet_lastname") @Parameter(description = "Vet last name") String vetLastname,
            @QueryParam("vet_id") @Parameter(description = "Vet id") String vetId,
            @QueryParam("client_name") @Parameter(description = "Client name") String clientName,
            @QueryParam("client_lastname") @Parameter(description = "Client last name") String clientLastname,
            @QueryParam("client_id") @Parameter(description = "Client id") String clientId,
            @QueryParam("appointment_type") @Parameter(description = "Appointment type") String appointmentType) {
        var validationErrors = new CopyOnWriteArrayList<ValidationError>();

        OrderType orderType = null;
        if (orderTypeStr != null)
            try {
                orderType = OrderType.valueOf(orderTypeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                validationErrors.add(new ValidationError("order query param",
                        "El tipo de orden no es válido, los valores permitidos son: "
                                + String.join(", ", EnumUtils.getEnumNames(OrderType.class, String::toLowerCase))));
            }

        validationErrors.addAll(validate(
                new SimpleValidation(page == null, "page query param", "La página es obligatoria"),
                new SimpleValidation(size == null, "size query param", "El tamaño es obligatorio"),
                new SimpleValidation(size != null && size > 10, "size query param", "El tamaño máximo es 10")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Criteria criteria = new Criteria(
                List.of(
                        new Filter("patient.name", FilterOperator.LIKE, patientName),
                        new Filter("patient.id", FilterOperator.LIKE, patientId),
                        new Filter("vet.id", FilterOperator.LIKE, vetId),
                        new Filter("vet.firstName", FilterOperator.LIKE, vetName),
                        new Filter("vet.lastName", FilterOperator.LIKE, vetLastname),
                        new Filter("patient.owner.id", FilterOperator.LIKE, clientId),
                        new Filter("patient.owner.firstName", FilterOperator.LIKE, clientName),
                        new Filter("patient.owner.lastName", FilterOperator.LIKE, clientLastname),
                        new Filter("details.type.name", FilterOperator.LIKE, appointmentType)),
                Order.of(orderBy, orderType),
                size,
                page);
        var result = loadAppointmentPort.match(criteria);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toPaginatedResponse(
                PaginatedAppointmentResponse.class,
                result.getSuccess(),
                "Citas encontradas exitosamente");
    }

    /**
     * Get appointment by id.
     */
    @Operation(summary = "Get appointment by id", description = "Get appointment by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment retrieved successfully.", content = @Content(schema = @Schema(implementation = AppointmentResponse.class))),
            @ApiResponse(responseCode = "404", description = "The appointment was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        var result = loadAppointmentPort.findById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentResponse.class,
                result.getSuccess(),
                "Cita encontrado exitosamente");
    }

    @Operation(summary = "Generate an Excel file with the appointments", description = "Generate an Excel file with the appointments.", responses = {
            @ApiResponse(responseCode = "200", description = "The Excel file was generated successfully.", content = @Content(schema = @Schema(implementation = InputStream.class))),
            @ApiResponse(responseCode = "500", description = "Error generating the Excel file.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @GET
    @Path("/excel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateExcel() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            LocalDateTime now = LocalDateTime.now();

            String fileName = String.format(
                    "Citas %d-%d-%d %d_%d_%d.xlsx",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getHour(),
                    now.getMinute(),
                    now.getSecond());

            generateAppointmentExcelPort.generateExcel(outputStream);

            return toFileResponse(
                    outputStream.toByteArray(),
                    fileName);
        } catch (IOException e) {
            return toFailureResponse(
                    "Error al generar el archivo Excel",
                    Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new appointment.
     *
     * @param request The appointment data.
     * @return The created appointment
     */
    @Operation(summary = "Create a new appointment", description = "Create a new appointment.", responses = {
            @ApiResponse(responseCode = "200", description = "The appointment was created successfully.", content = @Content(schema = @Schema(implementation = AppointmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateAppointmentDto request) {
        var validationErrors = request.validate();

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = createAppointmentPort.create(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentResponse.class,
                result.getSuccess(),
                "Cita creado exitosamente");
    }

    /**
     * Create a new detail for an appointment.
     *
     * @param request The appointment data.
     * @return The created appointment
     */
    @Operation(summary = "Create a new appointment", description = "Create a new appointment.", responses = {
            @ApiResponse(responseCode = "200", description = "The appointment was created successfully.", content = @Content(schema = @Schema(implementation = AppointmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @POST
    @Path("/{id}/details")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewDetails(
            @PathParam("id") Long id,
            CreateAppointmentDetailsDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(validate(
                new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                new SimpleValidation(id != null && !id.equals(request.getAppointmentId()), "id path param",
                        "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addDetailsToAppointmentPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentResponse.class,
                result.getSuccess(),
                "Cita creado exitosamente");
    }

    /**
     * Update a appointment.
     *
     * @param id The appointment id.
     * @return The updated appointment
     */
    @Operation(summary = "Update a appointment", description = "Update a appointment.", responses = {
            @ApiResponse(responseCode = "200", description = "The appointment was updated successfully.", content = @Content(schema = @Schema(implementation = AppointmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "The appointment was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, UpdateAppointmentDto request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = updateAppointmentPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                AppointmentResponse.class,
                result.getSuccess(),
                "Cita actualizada exitosamente");
    }

    /**
     * Delete a appointment.
     *
     * @param id The appointment id.
     * @return The deleted appointment
     */
    @Operation(summary = "Delete a appointment", description = "Delete a appointment.", responses = {
            @ApiResponse(responseCode = "200", description = "The appointment was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "The appointment was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        var result = deleteAppointmentPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Cita eliminada exitosamente");
    }
}