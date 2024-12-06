package com.vet.hc.api.medicalrecord.treatment.adapter.in.controller;

import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toDetailedFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toFailureResponse;
import static com.vet.hc.api.shared.adapter.in.util.ControllerShortcuts.toOkResponse;
import static com.vet.hc.api.shared.domain.validation.Validator.validate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vet.hc.api.auth.core.adapter.annotations.RestControllerAdapter;
import com.vet.hc.api.medicalrecord.treatment.adapter.in.request.UpdateTreatmentRequest;
import com.vet.hc.api.medicalrecord.treatment.adapter.in.response.TreatmentResponse;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.DeleteTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.UpdateTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
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
 * Medical treatment controller.
 */
@Tag(name = "Medical treatment", description = "Medical record treatment")
@RestControllerAdapter
@RequestMapping("/medicalrecord/treatment")
@RequiredArgsConstructor
public class TreatmentController {
    private final UpdateTreatmentPort updateTreatmentPort;
    private final DeleteTreatmentPort deleteTreatmentPort;

    /**
     * Update a treatment.
     *
     * @param id The treatment id.
     * @return The updated treatment
     */
    @Operation(summary = "Update a treatment", description = "Update a treatment.", responses = {
            @ApiResponse(responseCode = "200", description = "Medical record was updated successfully.", content = @Content(schema = @Schema(implementation = TreatmentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid treatment data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medical record was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateTreatmentRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<TreatmentDto, TreatmentFailure> result = updateTreatmentPort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                TreatmentResponse.class,
                result.getSuccess(),
                "Historial médico actualizado exitosamente");
    }

    /**
     * Delete a treatment.
     *
     * @param id The treatment id.
     * @return The deleted treatment
     */
    @Operation(summary = "Delete a treatment", description = "Delete a treatment.", responses = {
            @ApiResponse(responseCode = "200", description = "Treatment was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Treatment was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void, TreatmentFailure> result = deleteTreatmentPort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Historial médico eliminado exitosamente");
    }
}
