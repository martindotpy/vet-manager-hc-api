package com.vluepixel.vetmanager.api.appointment.details.adapter.in.controller;

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

import com.vluepixel.vetmanager.api.appointment.details.adapter.in.response.AppointmentDetailsResponse;
import com.vluepixel.vetmanager.api.appointment.details.adapter.in.response.PaginatedAppointmentDetailsResponse;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.CreateAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.DeleteAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.FindAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.application.port.in.UpdateAppointmentDetailsPort;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.CreateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.UpdateAppointmentDetailsRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Order;
import com.vluepixel.vetmanager.api.shared.domain.criteria.OrderType;
import com.vluepixel.vetmanager.api.shared.domain.criteria.PaginatedCriteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Appointment details controller.
 */
@Tag(name = "Appointment details", description = "Appointment details")
@RestControllerAdapter
@RequestMapping("/appointment/details")
@RequiredArgsConstructor
public final class AppointmentDetailsController {
    private final FindAppointmentDetailsPort findAppointmentDetailsPort;
    private final CreateAppointmentDetailsPort createAppointmentDetailsPort;
    private final UpdateAppointmentDetailsPort updateAppointmentDetailsPort;
    private final DeleteAppointmentDetailsPort deleteAppointmentDetailsPort;

    /**
     * Get all appointment details by paginated criteria.
     *
     * @param page                The page number.
     * @param size                The page size.
     * @param order               The order details.
     * @param orderBy             The order by field.
     * @param id                  The appointment details id.
     * @param appointmentTypeName The appointment type name.
     * @return The paginated appointment details response
     * @throws ValidationException If the page is less than 1, the id is less than
     *                             1, the size is less than 1, the order is defined
     *                             and the order_by is not defined.
     */
    @Operation(summary = "Get all appointment details by paginated criteria")
    @GetMapping
    public ResponseEntity<PaginatedAppointmentDetailsResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false, name = "appointment_type_name") String appointmentTypeName)
            throws ValidationException {
        return okPaginated(
                () -> findAppointmentDetailsPort.findPaginatedBy(
                        PaginatedCriteria.of(
                                page,
                                size,
                                Order.of(order, orderBy),
                                like("id", id),
                                like("type.name", appointmentTypeName))),
                "Tipos de cita encontradas",
                InvalidStateValidation.of(
                        order != null && orderBy == null,
                        "query.order",
                        "El campo para ordenar no puede ser nulo cuando se ha definido un orden"),
                InvalidStateValidation.of(
                        page < 1,
                        "query.page",
                        "La página no puede ser menor a 1"),
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"),
                InvalidStateValidation.of(
                        size < 1,
                        "query.size",
                        "El tamaño no puede ser menor a 1"));
    }

    /**
     * Create an appointment details.
     *
     * @param request The create appointment details request.
     * @return The appointment details response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create an appointment details")
    @PostMapping
    public ResponseEntity<AppointmentDetailsResponse> create(@RequestBody CreateAppointmentDetailsRequest request)
            throws ValidationException {
        return ok(() -> createAppointmentDetailsPort.create(request),
                "Detalles de la cita creado",
                ValidationRequest.of(request));
    }

    /**
     * Update an appointment details.
     *
     * @param request The update appointment details request.
     * @return The appointment details response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update an appointment details")
    @PutMapping
    public ResponseEntity<AppointmentDetailsResponse> update(@RequestBody UpdateAppointmentDetailsRequest request)
            throws ValidationException {
        return ok(() -> updateAppointmentDetailsPort.update(request),
                "Detalles de la cita actualizado",
                ValidationRequest.of(request));
    }

    /**
     * Delete an appointment details.
     *
     * @param id The appointment details id.
     * @return The appointment details response.
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete an appointment details")
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long id)
            throws NotFoundException {
        return ok(() -> deleteAppointmentDetailsPort.deleteById(id),
                "Detalles de la cita eliminado",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }
}
