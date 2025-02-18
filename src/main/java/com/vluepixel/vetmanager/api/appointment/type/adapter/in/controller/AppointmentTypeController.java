package com.vluepixel.vetmanager.api.appointment.type.adapter.in.controller;

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

import com.vluepixel.vetmanager.api.appointment.type.adapter.in.response.AppointmentTypeResponse;
import com.vluepixel.vetmanager.api.appointment.type.adapter.in.response.PaginatedAppointmentTypeResponse;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.CreateAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.DeleteAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.FindAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.application.port.in.UpdateAppointmentTypePort;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.CreateAppointmentTypeRequest;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.UpdateAppointmentTypeRequest;
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
 * Appointment type controller.
 */
@Tag(name = "Appointment type", description = "Appointment type")
@RestControllerAdapter
@RequestMapping("/appointment/type")
@RequiredArgsConstructor
public final class AppointmentTypeController {
    private final FindAppointmentTypePort findAppointmentTypePort;
    private final CreateAppointmentTypePort createAppointmentTypePort;
    private final UpdateAppointmentTypePort updateAppointmentTypePort;
    private final DeleteAppointmentTypePort deleteAppointmentTypePort;

    /**
     * Get all appointment types by paginated criteria.
     *
     * @param page    The page number.
     * @param size    The page size.
     * @param order   The order type.
     * @param orderBy The order by field.
     * @param id      The appointment type id.
     * @param name    The appointment type name.
     * @return paginated response with the appointment types found
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all appointment types by paginated criteria", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment types found"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PaginatedAppointmentTypeResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name)
            throws ValidationException {
        return okPaginated(
                findAppointmentTypePort::findPaginatedBy,
                page,
                size,
                Order.of(order, orderBy),
                Criteria.of(
                        like("id", id),
                        like("name", name)),
                "Tipos de cita encontradas",
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Get an appointment type by id.
     *
     * @param id The appointment type id.
     * @return response with the appointment type found
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the appointment type is not found.
     */
    @Operation(summary = "Get an appointment type by id", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type found"),
            @ApiResponse(responseCode = "404", description = "Appointment type not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentTypeResponse> getById(@PathVariable Integer id)
            throws ValidationException, NotFoundException {
        return ok(() -> findAppointmentTypePort.findById(id),
                "Tipo de cita encontrada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }

    /**
     * Create an appointment type.
     *
     * @param request The create appointment type request.
     * @return response with the created appointment type
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create an appointment type", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type created"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AppointmentTypeResponse> create(@RequestBody CreateAppointmentTypeRequest request)
            throws ValidationException {
        return ok(() -> createAppointmentTypePort.create(request),
                "Tipo de cita creada",
                ValidationRequest.of(request));
    }

    /**
     * Update an appointment type.
     *
     * @param request The update appointment type request.
     * @return response with the updated appointment type
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the appointment type is not found.
     */
    @Operation(summary = "Update an appointment type", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type updated"),
            @ApiResponse(responseCode = "404", description = "Appointment type not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping
    public ResponseEntity<AppointmentTypeResponse> update(@RequestBody UpdateAppointmentTypeRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> updateAppointmentTypePort.update(request),
                "Tipo de cita actualizada",
                ValidationRequest.of(request));
    }

    /**
     * Delete an appointment type.
     *
     * @param id The appointment type id.
     * @return response with an ok message
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the appointment type is not found.
     */
    @Operation(summary = "Delete an appointment type", responses = {
            @ApiResponse(responseCode = "200", description = "Appointment type deleted"),
            @ApiResponse(responseCode = "404", description = "Appointment type not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id)
            throws ValidationException, NotFoundException {
        return ok(() -> deleteAppointmentTypePort.deleteById(id),
                "Tipo de cita eliminada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
