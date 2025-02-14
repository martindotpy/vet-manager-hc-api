package com.vluepixel.vetmanager.api.vaccine.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;
import com.vluepixel.vetmanager.api.vaccine.core.adapter.in.response.VaccineResponse;
import com.vluepixel.vetmanager.api.vaccine.core.adapter.in.response.VaccinesResponse;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.CreateVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.DeleteVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.FindVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.UpdateVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.CreateVaccineRequest;
import com.vluepixel.vetmanager.api.vaccine.core.domain.request.UpdateVaccineRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Vaccine controller.
 */
@Tag(name = "Vaccine", description = "Vaccine")
@RestControllerAdapter
@RequiredArgsConstructor
public final class VaccineController {
    private final FindVaccinePort findVaccinePort;
    private final CreateVaccinePort createVaccinePort;
    private final UpdateVaccinePort updateVaccinePort;
    private final DeleteVaccinePort deleteVaccinePort;

    /**
     * Get all vaccines by patient id.
     *
     * @param patientId The patient id.
     * @return The vaccines response
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Get all vaccine by patient id")
    @GetMapping("/patient/{patientId}/vaccine")
    public ResponseEntity<VaccinesResponse> getByPatientId(@PathVariable Long patientId)
            throws ValidationException {
        return ok(() -> findVaccinePort.findAllByPatientId(patientId),
                "Historiales médicos del paciente obtenidos exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente no puede ser menor a 1"));
    }

    /**
     * Create a vaccine.
     *
     * @param request The create vaccine request.
     * @return The vaccine response
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a vaccine")
    @PostMapping("/patient/{patientId}/vaccine")
    public ResponseEntity<VaccineResponse> create(@RequestBody CreateVaccineRequest request)
            throws ValidationException {
        return ok(() -> createVaccinePort.create(request),
                "Historial médico creado exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Update a vaccine.
     *
     * @param request The update vaccine request.
     * @return The vaccine response
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update a vaccine")
    @PutMapping("/patient/{patientId}/vaccine")
    public ResponseEntity<VaccineResponse> update(@RequestBody UpdateVaccineRequest request)
            throws ValidationException {
        return ok(() -> updateVaccinePort.update(request),
                "Historial médico actualizado exitosamente",
                ValidationRequest.of(request));
    }

    /**
     * Delete a vaccine by patient id.
     *
     * @param patientId The patient id.
     * @param id        The vaccine id.
     * @return The vaccine response
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a vaccine")
    @DeleteMapping("/patient/{patientId}/vaccine/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long patientId, @PathVariable Long id)
            throws NotFoundException {
        return ok(() -> deleteVaccinePort.deleteByPatientIdAndId(patientId, id),
                "Historial médico eliminado exitosamente",
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }
}
