package com.vluepixel.vetmanager.api.medicalrecord.core.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vluepixel.vetmanager.api.medicalrecord.core.adapter.in.response.MedicalRecordResponse;
import com.vluepixel.vetmanager.api.medicalrecord.core.adapter.in.response.MedicalRecordsResponse;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.CreateMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.DeleteMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.FindMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.UpdateMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.CreateMedicalRecordRequest;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.UpdateMedicalRecordRequest;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.BasicResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.FailureResponse;
import com.vluepixel.vetmanager.api.shared.application.annotation.RestControllerAdapter;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.exception.ValidationException;
import com.vluepixel.vetmanager.api.shared.domain.validation.ValidationRequest;
import com.vluepixel.vetmanager.api.shared.domain.validation.impl.InvalidStateValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Medical record controller.
 */
@Tag(name = "Medical record", description = "Medical record")
@RestControllerAdapter
@RequiredArgsConstructor
public final class MedicalRecordController {
    private final FindMedicalRecordPort findMedicalRecordPort;
    private final CreateMedicalRecordPort createMedicalRecordPort;
    private final UpdateMedicalRecordPort updateMedicalRecordPort;
    private final DeleteMedicalRecordPort deleteMedicalRecordPort;

    /**
     * Get all medical records by patient id.
     *
     * @param patientId The patient id.
     * @return response with the medical records found
     * @throws ValidationException If the id is less than 1.
     */
    @Operation(summary = "Get all medical record by patient id", responses = {
            @ApiResponse(responseCode = "200", description = "Medical records found"),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping("/patient/{patient_id}/record")
    public ResponseEntity<MedicalRecordsResponse> getByPatientId(@PathVariable(name = "patient_id") Long patientId)
            throws ValidationException {
        return ok(() -> findMedicalRecordPort.findAllByPatientId(patientId),
                "Historiales médicos del paciente obtenidos exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"));
    }

    /**
     * Create a medical record.
     *
     * @param patientId The patient id.
     * @param request   The create medical record request.
     * @return response with the medical record created
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the patient is not found.
     */
    @Operation(summary = "Create a medical record", responses = {
            @ApiResponse(responseCode = "200", description = "Medical record created"),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PostMapping("/patient/{patient_id}/record")
    public ResponseEntity<MedicalRecordResponse> create(
            @PathVariable(name = "patient_id") Long patientId,
            @RequestBody CreateMedicalRecordRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> createMedicalRecordPort.create(request),
                "Registro médico creado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"),
                InvalidStateValidation.of(
                        !patientId.equals(request.getPatientId()),
                        "path.patient_id",
                        "El id del paciente no coincide con el id del historial médico"),
                ValidationRequest.of(request));
    }

    /**
     * Update a medical record.
     *
     * @param patientId The patient id.
     * @param request   The update medical record request.
     * @return response with the medical record updated
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the patient or medical record is not found.
     */
    @Operation(summary = "Update a medical record", responses = {
            @ApiResponse(responseCode = "200", description = "Medical record updated"),
            @ApiResponse(responseCode = "404", description = "Patient or medical record not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping("/patient/{patient_id}/record")
    public ResponseEntity<MedicalRecordResponse> update(
            @PathVariable(name = "patient_id") Long patientId,
            @RequestBody UpdateMedicalRecordRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> updateMedicalRecordPort.update(patientId, request),
                "Registro médico actualizado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"),
                ValidationRequest.of(request));
    }

    /**
     * Delete a medical record by patient id.
     *
     * @param patientId The patient id.
     * @param id        The medical record id.
     * @return response with an ok message
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the patient or medical record is not found.
     */
    @Operation(summary = "Delete a medical record", responses = {
            @ApiResponse(responseCode = "200", description = "Medical record deleted"),
            @ApiResponse(responseCode = "404", description = "Patient or medical record not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @DeleteMapping("/patient/{patient_id}/record/{id}")
    public ResponseEntity<BasicResponse> delete(
            @PathVariable(name = "patient_id") Long patientId,
            @PathVariable Long id)
            throws ValidationException, NotFoundException {
        return ok(() -> deleteMedicalRecordPort.deleteByPatientIdAndId(patientId, id),
                "Registro médico eliminado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"),
                InvalidStateValidation.of(
                        id < 1,
                        "query.id",
                        "El id debe ser mayor a 0"));
    }
}
