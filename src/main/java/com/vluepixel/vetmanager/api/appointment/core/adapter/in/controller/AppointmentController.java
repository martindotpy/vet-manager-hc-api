package com.vluepixel.vetmanager.api.appointment.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.okPaginated;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vluepixel.vetmanager.api.appointment.core.adapter.in.response.AppointmentResponse;
import com.vluepixel.vetmanager.api.appointment.core.adapter.in.response.PaginatedAppointmentResponse;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.CreateAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.DeleteAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.FindAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.application.port.in.UpdateAppointmentPort;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.CreateAppointmentRequest;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.UpdateAppointmentRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Appointment controller.
 */
@Tag(name = "Appointment", description = "Appointment")
@RestControllerAdapter
@RequestMapping("/appointment")
@RequiredArgsConstructor
public final class AppointmentController {
    private final FindAppointmentPort findAppointmentPort;
    private final CreateAppointmentPort createAppointmentPort;
    private final UpdateAppointmentPort updateAppointmentPort;
    private final DeleteAppointmentPort deleteAppointmentPort;

    /**
     * Get all appointment by paginated criteria.
     *
     * @param page                The page number.
     * @param size                The page size.
     * @param order               The order.
     * @param orderBy             The order by field.
     * @param id                  The appointment id.
     * @param appointmentTypeName The appointment type name.
     * @return paginated response with the appointments found
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all appointment by paginated criteria", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PaginatedAppointmentResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false, name = "appointment_type_name") String appointmentTypeName)
            throws ValidationException {
        return okPaginated(
                findAppointmentPort::findPaginatedBy,
                page,
                size,
                Order.of(order, orderBy),
                Criteria.of(
                        like("id", id),
                        like("type.name", appointmentTypeName)),
                "Citas encontradas",
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Get an appointment by id.
     *
     * @param id The appointment id.
     * @return response with the appointment found.
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the appointment is not found.
     */
    @Operation(summary = "Get an appointment by id", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment found"),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id)
            throws ValidationException, NotFoundException {
        return ok(() -> findAppointmentPort.findById(id),
                "Cita encontrada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Create an appointment.
     *
     * @param request The create appointment request.
     * @return response with the appointment created
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create an appointment", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment created"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@RequestBody CreateAppointmentRequest request)
            throws ValidationException {
        return ok(() -> createAppointmentPort.create(request),
                "Cita creada",
                ValidationRequest.of(request));
    }

    /**
     * Update an appointment.
     *
     * @param request The update appointment request.
     * @return response with the appointment updated
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the appointment is not found.
     */
    @Operation(summary = "Update an appointment", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment updated"),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping
    public ResponseEntity<AppointmentResponse> update(@RequestBody UpdateAppointmentRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> updateAppointmentPort.update(request),
                "Cita actualizada",
                ValidationRequest.of(request));
    }

    /**
     * Delete an appointment.
     *
     * @param id The appointment id.
     * @return response with an ok message
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete an appointment", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted"),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long id)
            throws ValidationException, NotFoundException {
        return ok(() -> deleteAppointmentPort.deleteById(id),
                "Cita eliminada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
