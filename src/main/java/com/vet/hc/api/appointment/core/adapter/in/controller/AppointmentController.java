package com.vet.hc.api.appointment.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFileResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toPaginatedResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vet.hc.api.appointment.core.adapter.in.request.CreateAppointmentRequest;
import com.vet.hc.api.appointment.core.adapter.in.request.UpdateAppointmentRequest;
import com.vet.hc.api.appointment.core.application.port.in.AddDetailsToAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.CreateAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.DeleteAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vet.hc.api.appointment.core.application.port.in.GenerateAppointmentExcelPort;
import com.vet.hc.api.appointment.core.application.port.in.UpdateAppointmentPort;
import com.vet.hc.api.appointment.core.application.response.AppointmentResponse;
import com.vet.hc.api.appointment.core.application.response.PaginatedAppointmentResponse;
import com.vet.hc.api.appointment.details.adapter.in.request.CreateAppointmentDetailsRequest;
import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
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
import lombok.RequiredArgsConstructor;

/**
 * Appointment controller.
 */
@Tag(name = "Appointment", description = "Veterinary appointment")
@RestControllerAdapter
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final CreateAppointmentPort createAppointmentPort;
    private final AddDetailsToAppointmentPort addDetailsToAppointmentPort;
    private final FindAppointmentPort loadAppointmentPort;
    private final UpdateAppointmentPort updateAppointmentPort;
    private final DeleteAppointmentPort deleteAppointmentPort;
    private final GenerateAppointmentExcelPort generateAppointmentExcelPort;

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
    @GetMapping
    public ResponseEntity<?> getAllByCriteria(
            @RequestParam @Parameter(required = true, description = "Page number (min 1)") Integer page,
            @RequestParam @Parameter(required = true, description = "Page size (max 10 elements)") Integer size,
            @RequestParam(value = "order_by", required = false) @Parameter(description = "Field to order by. The field must be in snake case") String orderBy,
            @RequestParam(value = "order", required = false) @Parameter(description = "Order type, if it is empty, it will be 'none'") String orderTypeStr,
            @RequestParam(value = "patient_name", required = false) @Parameter(description = "Patient name") String patientName,
            @RequestParam(value = "patient_id", required = false) @Parameter(description = "Patient id") String patientId,
            @RequestParam(value = "vet_name", required = false) @Parameter(description = "Vet name") String vetName,
            @RequestParam(value = "vet_lastname", required = false) @Parameter(description = "Vet last name") String vetLastname,
            @RequestParam(value = "vet_id", required = false) @Parameter(description = "Vet id") String vetId,
            @RequestParam(value = "client_name", required = false) @Parameter(description = "Client name") String clientName,
            @RequestParam(value = "client_lastname", required = false) @Parameter(description = "Client last name") String clientLastname,
            @RequestParam(value = "client_id", required = false) @Parameter(description = "Client id") String clientId,
            @RequestParam(value = "appointment_type", required = false) @Parameter(description = "Appointment type") String appointmentType) {
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
                new SimpleValidation(page != null && page < 1, "page query param", "La página debe ser mayor a 0"),
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
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
    @GetMapping("/excel")
    public ResponseEntity<?> generateExcel() {
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
                    HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PostMapping
    public ResponseEntity<?> create(CreateAppointmentRequest request) {
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
    @PostMapping("/{id}/details")
    public ResponseEntity<?> addNewDetails(
            @PathVariable Long id,
            @RequestBody CreateAppointmentDetailsRequest request) {
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
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, UpdateAppointmentRequest request) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var result = deleteAppointmentPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Cita eliminada exitosamente");
    }
}