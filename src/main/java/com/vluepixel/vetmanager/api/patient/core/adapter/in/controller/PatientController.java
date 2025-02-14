package com.vluepixel.vetmanager.api.patient.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;
import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.okPaginated;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vluepixel.vetmanager.api.patient.core.adapter.in.response.PaginatedPatientResponse;
import com.vluepixel.vetmanager.api.patient.core.adapter.in.response.PatientResponse;
import com.vluepixel.vetmanager.api.patient.core.application.port.in.CreatePatientPort;
import com.vluepixel.vetmanager.api.patient.core.application.port.in.DeletePatientPort;
import com.vluepixel.vetmanager.api.patient.core.application.port.in.FindPatientPort;
import com.vluepixel.vetmanager.api.patient.core.application.port.in.UpdatePatientPort;
import com.vluepixel.vetmanager.api.patient.core.domain.enums.PatientGender;
import com.vluepixel.vetmanager.api.patient.core.domain.request.CreatePatientRequest;
import com.vluepixel.vetmanager.api.patient.core.domain.request.UpdatePatientRequest;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Patient controller.
 */
@Tag(name = "Patient", description = "Patient")
@RestControllerAdapter
@RequestMapping("/patient")
@RequiredArgsConstructor
public final class PatientController {
    private final FindPatientPort findPatientPort;
    private final CreatePatientPort createPatientPort;
    private final UpdatePatientPort updatePatientPort;
    private final DeletePatientPort deletePatientPort;

    /**
     * Get all patient by paginated criteria.
     *
     * @param page                     The page number.
     * @param size                     The page size.
     * @param order                    The order type.
     * @param orderBy                  The order by field.
     * @param id                       The patient id.
     * @param name                     The patient name.
     * @param age                      The patient age.
     * @param gender                   The patient gender.
     * @param deceased                 The patient deceased status.
     * @param raceId                   The race id.
     * @param raceName                 The race name.
     * @param speciesId                The species id.
     * @param speciesName              The species name.
     * @param clientId                 The client id.
     * @param clientFirstName          The client first name.
     * @param clientLastName           The client last name.
     * @param clientIdentification     The client identification.
     * @param clientIdentificationType The client identification type.
     * @param clientAddress            The client address.
     * @param clientPhone              The client phone.
     * @param clientEmail              The client email.
     * @return The paginated patient response.
     * @throws ValidationException If the request parameters are invalid.
     */
    @Operation(summary = "Get all patient by paginated criteria")
    @GetMapping
    public ResponseEntity<PaginatedPatientResponse> getByPaginatedCriteria(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) OrderType order,
            @RequestParam(required = false, name = "order_by") String orderBy,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) PatientGender gender,
            @RequestParam(required = false) Boolean deceased,
            @RequestParam(required = false, name = "race_id") String raceId,
            @RequestParam(required = false, name = "race_name") String raceName,
            @RequestParam(required = false, name = "species_id") String speciesId,
            @RequestParam(required = false, name = "species_name") String speciesName,
            @RequestParam(required = false, name = "client_id") String clientId,
            @RequestParam(required = false, name = "client_first_name") String clientFirstName,
            @RequestParam(required = false, name = "client_last_name") String clientLastName,
            @RequestParam(required = false, name = "client_identification") String clientIdentification,
            @RequestParam(required = false, name = "client_identification_type") String clientIdentificationType,
            @RequestParam(required = false, name = "client_address") String clientAddress,
            @RequestParam(required = false, name = "client_phone") String clientPhone,
            @RequestParam(required = false, name = "client_email") String clientEmail)
            throws ValidationException {
        return okPaginated(
                findPatientPort::findPaginatedBy,
                page,
                size,
                Order.of(order, orderBy),
                Criteria.of(
                        like("id", id),
                        like("name", name),
                        like("age", age),
                        like("gender", gender),
                        like("deceased", deceased),
                        like("race.id", raceId),
                        like("race.name", raceName),
                        like("race.species.id", speciesId),
                        like("race.species.name", speciesName),
                        like("client.id", clientId),
                        like("client.firstName", clientFirstName),
                        like("client.lastName", clientLastName),
                        like("client.identification", clientIdentification),
                        like("client.identificationType", clientIdentificationType),
                        like("client.address", clientAddress),
                        like("client.phone", clientPhone),
                        like("client.email", clientEmail)),
                "Razas encontradas exitosamente",
                InvalidStateValidation.of(
                        id != null && id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }

    /**
     * Create a patient.
     *
     * @param request The create patient request.
     * @return The patient response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a patient")
    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody CreatePatientRequest request)
            throws ValidationException {
        return ok(() -> createPatientPort.create(request),
                "Paciente creado exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Update a patient.
     *
     * @param request The update patient request.
     * @return The patient response.
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update a patient")
    @PutMapping
    public ResponseEntity<PatientResponse> update(@RequestBody UpdatePatientRequest request)
            throws ValidationException {
        return ok(() -> updatePatientPort.update(request),
                "Paciente eliminado exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Delete a patient.
     *
     * @param id The patient id.
     * @return The patient response.
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a patient")
    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long id)
            throws NotFoundException {
        return ok(() -> deletePatientPort.deleteById(id),
                "Paciente eliminado exitosamente",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }
}
