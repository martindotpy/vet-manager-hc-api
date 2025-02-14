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
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
     * @return The paginated appointment type response
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all appointment types by paginated criteria")
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
                        "El id no puede ser menor a 1"));
    }

    /**
     * Create an appointment type.
     *
     * @param request The create appointment type request.
     * @return The appointment type response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create an appointment type")
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
     * @return The appointment type response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update an appointment type")
    @PutMapping
    public ResponseEntity<AppointmentTypeResponse> update(@RequestBody UpdateAppointmentTypeRequest request)
            throws ValidationException {
        return ok(() -> updateAppointmentTypePort.update(request),
                "Tipo de cita actualizada",
                ValidationRequest.of(request));
    }

    /**
     * Delete an appointment type.
     *
     * @param id The appointment type id.
     * @return The appointment type response.
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete an appointment type")
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long id)
            throws NotFoundException {
        return ok(() -> deleteAppointmentTypePort.deleteById(id),
                "Tipo de cita eliminada",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }
}
