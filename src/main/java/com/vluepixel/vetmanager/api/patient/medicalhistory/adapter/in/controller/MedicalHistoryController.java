package com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.in.controller;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.in.response.MedicalHistoriesResponse;
import com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.in.response.MedicalHistoryResponse;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.CreateMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.DeleteMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.FindMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.UpdateMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.CreateMedicalHistoryRequest;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.UpdateMedicalHistoryRequest;
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
 * Medical history controller.
 */
@Tag(name = "Medical history", description = "Medical history")
@RestControllerAdapter
@RequiredArgsConstructor
public final class MedicalHistoryController {
    private final FindMedicalHistoryPort findMedicalHistoryPort;
    private final CreateMedicalHistoryPort createMedicalHistoryPort;
    private final UpdateMedicalHistoryPort updateMedicalHistoryPort;
    private final DeleteMedicalHistoryPort deleteMedicalHistoryPort;

    /**
     * Get all medical histories by patient id.
     *
     * @param patientId The patient id.
     * @return response with the medical histories found
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the patient is not found.
     */
    @Operation(summary = "Get all medical history by patient id", responses = {
            @ApiResponse(responseCode = "200", description = "Medical histories found"),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @GetMapping("/patient/{patient_id}/history")
    public ResponseEntity<MedicalHistoriesResponse> getByPatientId(
            @PathVariable(name = "patient_id") Long patientId)
            throws ValidationException, NotFoundException {
        return ok(() -> findMedicalHistoryPort.findAllByPatientId(patientId),
                "Historiales médicos del paciente obtenidos exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"));
    }

    /**
     * Create a medical history.
     *
     * @param patientId The patient id.
     * @param request   The create medical history request.
     * @return response with the created medical history
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the patient is not found.
     */
    @Operation(summary = "Create a medical history", responses = {
            @ApiResponse(responseCode = "200", description = "Medical history created"),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PostMapping("/patient/{patient_id}/history")
    public ResponseEntity<MedicalHistoryResponse> create(
            @PathVariable(name = "patient_id") Long patientId,
            @RequestBody CreateMedicalHistoryRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> createMedicalHistoryPort.create(request),
                "Historial médico creado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"),
                InvalidStateValidation.of(
                        !patientId.equals(request.getPatientId()),
                        "body.patient_id",
                        "El id del paciente no coincide con el id del paciente en la ruta"),
                ValidationRequest.of(request));
    }

    /**
     * Update a medical history.
     *
     * @param patientId The patient id.
     * @param request   The update medical history request.
     * @return response with the updated medical history
     * @throws ValidationException If the request is invalid.
     * @throws NotFoundException   If the patient or the medical history is not
     *                             found.
     */
    @Operation(summary = "Update a medical history", responses = {
            @ApiResponse(responseCode = "200", description = "Medical history updated"),
            @ApiResponse(responseCode = "404", description = "Patient or medical history not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @PutMapping("/patient/{patient_id}/history")
    public ResponseEntity<MedicalHistoryResponse> update(
            @PathVariable(name = "patient_id") Long patientId,
            @RequestBody UpdateMedicalHistoryRequest request)
            throws ValidationException, NotFoundException {
        return ok(() -> updateMedicalHistoryPort.update(patientId, request),
                "Historial médico actualizado exitosamente",
                InvalidStateValidation.of(
                        patientId < 1,
                        "path.patient_id",
                        "El id del paciente debe ser mayor a 0"),
                ValidationRequest.of(request));
    }

    /**
     * Delete a medical history by patient id.
     *
     * @param patientId The patient id.
     * @param id        The medical history id.
     * @return response with an ok message
     * @throws ValidationException If the id is less than 1.
     * @throws NotFoundException   If the patient or the medical history is not
     *                             found.
     */
    @Operation(summary = "Delete a medical history", responses = {
            @ApiResponse(responseCode = "200", description = "Medical history deleted"),
            @ApiResponse(responseCode = "404", description = "Patient or medical history not found", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
            @ApiResponse(responseCode = "422", description = "Validation error", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class)))
    })
    @DeleteMapping("/patient/{patient_id}/history/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Long patientId, @PathVariable Long id)
            throws NotFoundException, NotFoundException {
        return ok(() -> deleteMedicalHistoryPort.deleteByPatientIdAndId(patientId, id),
                "Historial médico eliminado exitosamente",
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
