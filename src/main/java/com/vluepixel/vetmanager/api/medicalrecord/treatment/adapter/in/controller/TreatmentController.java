package com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.in.response.TreatmentResponse;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.in.response.TreatmentsResponse;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.CreateTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.DeleteTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.FindTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.UpdateTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request.CreateTreatmentRequest;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request.UpdateTreatmentRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Treatment controller.
 */
@Tag(name = "Treatment", description = "Treatment")
@RestControllerAdapter
@RequiredArgsConstructor
public final class TreatmentController {
    private final FindTreatmentPort findTreatmentPort;
    private final CreateTreatmentPort createTreatmentPort;
    private final UpdateTreatmentPort updateTreatmentPort;
    private final DeleteTreatmentPort deleteTreatmentPort;

    /**
     * Get all treatments by patient id and medical record id.
     *
     * @param patientId The patient id.
     * @return The treatments response
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Get all treatment by patient id")
    @GetMapping("/patient/{patient_id}/record/{record_id}/treatment")
    public ResponseEntity<TreatmentsResponse> getByPatientIdAndMedicalRecordId(
            @PathVariable(name = "patient_id") Long patientId,
            @PathVariable(name = "record_id") Long recordId)
            throws ValidationException {
        return ok(() -> findTreatmentPort.findAllByPatientIdAndMedicalRecordId(patientId, recordId),
                "Historiales médicos del paciente obtenidos exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente no puede ser menor a 1"),
                InvalidStateValidation.of(
                        recordId < 1,
                        "path.record_id",
                        "El id del historial médico no puede ser menor a 1"));
    }

    /**
     * Create a treatment.
     *
     * @param patientId The patient id.
     * @param request   The create treatment request.
     * @return The treatment response
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Create a treatment")
    @PostMapping("/patient/{patient_id}/record/{record_id}/treatment")
    public ResponseEntity<TreatmentResponse> create(
            @PathVariable(name = "patient_id") Long patientId,
            @PathVariable(name = "record_id") Long recordId,
            @RequestBody CreateTreatmentRequest request)
            throws ValidationException {
        return ok(() -> createTreatmentPort.create(request),
                "Tratamiento creado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente no puede ser menor a 1"),
                InvalidStateValidation.of(
                        recordId < 1,
                        "path.record_id",
                        "El id del historial médico no puede ser menor a 1"),
                InvalidStateValidation.of(
                        !recordId.equals(request.getMedicalRecordId()),
                        "path.record_id",
                        "El id del historial médico no coincide con el id del registro médico"),
                ValidationRequest.of(request));
    }

    /**
     * Update a treatment.
     *
     * @param patientId The patient id.
     * @param request   The update treatment request.
     * @return The treatment response
     * @throws ValidationException If the request is invalid.
     */
    @Operation(summary = "Update a treatment")
    @PutMapping("/patient/{patient_id}/record/{record_id}/treatment")
    public ResponseEntity<TreatmentResponse> update(
            @PathVariable(name = "patient_id") Long patientId,
            @PathVariable(name = "record_id") Long recordId,
            @RequestBody UpdateTreatmentRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> updateTreatmentPort.update(patientId, recordId, request),
                "Tratamiento actualizado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente no puede ser menor a 1"),
                ValidationRequest.of(request));
    }

    /**
     * Delete a treatment by patient id.
     *
     * @param patientId The patient id.
     * @param id        The treatment id.
     * @return The treatment response
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Delete a treatment")
    @DeleteMapping("/patient/{patient_id}/record/{record_id}/treatment/{id}")
    public ResponseEntity<BasicResponse> delete(
            @PathVariable(name = "patient_id") Long patientId,
            @PathVariable(name = "record_id") Long recordId,
            @PathVariable Long id)
            throws ValidationException, NotFoundException {
        return ok(() -> deleteTreatmentPort.deleteByPatientIdAndMedicalRecordIdAndId(patientId, recordId, id),
                "Tratamiento eliminado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente no puede ser menor a 1"),
                InvalidStateValidation.of(
                        recordId < 1,
                        "path.record_id",
                        "El id del historial médico no puede ser menor a 1"),
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id no puede ser menor a 1"));
    }
}
