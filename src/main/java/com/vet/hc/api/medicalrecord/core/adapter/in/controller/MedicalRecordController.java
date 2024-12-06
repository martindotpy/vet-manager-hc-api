package com.vet.hc.api.medicalrecord.core.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
import com.vet.hc.api.medicalrecord.core.adapter.in.request.UpdateMedicalRecordRequest;
import com.vet.hc.api.medicalrecord.core.adapter.in.response.MedicalRecordResponse;
import com.vet.hc.api.medicalrecord.core.application.port.in.AddTreatmentToMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.application.port.in.DeleteMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.application.port.in.UpdateMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.treatment.adapter.in.request.CreateTreatmentRequest;
import com.vet.hc.api.shared.adapter.in.response.BasicResponse;
import com.vet.hc.api.shared.adapter.in.response.DetailedFailureResponse;
import com.vet.hc.api.shared.adapter.in.response.FailureResponse;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.validation.SimpleValidation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Medical record controller.
 */
@Tag(name = "Medical record", description = "Patient medical record")
@RestControllerAdapter
@RequestMapping("/medicalrecord")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final AddTreatmentToMedicalRecordPort addTreatmentToMedicalRecordPort;
    private final UpdateMedicalRecordPort updateMedicalRecordPort;
    private final DeleteMedicalRecordPort deleteMedicalRecordPort;

    /**
     * Update a medical record.
     *
     * @param id The medical record id.
     * @return The updated medical record
     */
    @Operation(summary = "Update a medical record", description = "Update a medical record.", responses = {
            @ApiResponse(responseCode = "200", description = "Medical record was updated successfully.", content = @Content(schema = @Schema(implementation = MedicalRecordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid medical record data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medical record was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateMedicalRecordRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<MedicalRecordDto, MedicalRecordFailure> result = updateMedicalRecordPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                MedicalRecordResponse.class,
                result.getSuccess(),
                "Historial médico actualizado exitosamente");
    }

    /**
     * Create a new treatment for a medical record.
     *
     * @param request The treatment data.
     * @return The updated medical record
     */
    @Operation(summary = "Create a new treatment", description = "Create a new treatment.", responses = {
            @ApiResponse(responseCode = "200", description = "The treatment was created successfully.", content = @Content(schema = @Schema(implementation = MedicalRecordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid treatment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
    })
    @PostMapping("/{id}/treatment")

    public ResponseEntity<?> addTreatment(
            @PathVariable Long id,
            @RequestBody CreateTreatmentRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(validate(
                new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                new SimpleValidation(id != null && !id.equals(request.getMedicalRecordId()), "id path param",
                        "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        var result = addTreatmentToMedicalRecordPort.add(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                MedicalRecordResponse.class,
                result.getSuccess(),
                "Tratamiento añadido exitosamente");
    }

    /**
     * Delete a medical record.
     *
     * @param id The medical record id.
     * @return The deleted medical record
     */
    @Operation(summary = "Delete a medical record", description = "Delete a medical record.", responses = {
            @ApiResponse(responseCode = "200", description = "MedicalRecord was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "MedicalRecord was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void, MedicalRecordFailure> result = deleteMedicalRecordPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Historial médico eliminado exitosamente");
    }
}
