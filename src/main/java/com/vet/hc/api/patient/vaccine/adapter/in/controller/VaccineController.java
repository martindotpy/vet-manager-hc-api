package com.vet.hc.api.patient.vaccine.adapter.in.controller;

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
import com.vet.hc.api.patient.vaccine.adapter.in.request.UpdateVaccineRequest;
import com.vet.hc.api.patient.vaccine.adapter.in.response.VaccineResponse;
import com.vet.hc.api.patient.vaccine.application.port.in.DeleteVaccinePort;
import com.vet.hc.api.patient.vaccine.application.port.in.UpdateVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
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
 * Vaccine controller.
 */
@Tag(name = "Vaccine", description = "Patient vaccine")
@RestControllerAdapter
@RequestMapping("/patient/vaccine")
@RequiredArgsConstructor
public class VaccineController {
    private final UpdateVaccinePort updateVaccinePort;
    private final DeleteVaccinePort deleteVaccinePort;

    /**
     * Update a vaccine.
     *
     * @param id The vaccine id.
     * @return The updated vaccine
     */
    @Operation(summary = "Update a vaccine", description = "Update a vaccine.", responses = {
            @ApiResponse(responseCode = "200", description = "Vaccine was updated successfully.", content = @Content(schema = @Schema(implementation = VaccineResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid vaccine data.", content = @Content(schema = @Schema(implementation = DetailedFailureResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vaccine was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateVaccineRequest request) {
        var validationErrors = request.validate();

        validationErrors.addAll(
                validate(
                        new SimpleValidation(id == null, "id path param", "El id es obligatorio"),
                        new SimpleValidation(id != null && !id.equals(request.getId()), "id path param",
                                "El id del cuerpo y el id de la URL no coinciden")));

        if (!validationErrors.isEmpty())
            return toDetailedFailureResponse(validationErrors);

        Result<VaccineDto, VaccineFailure> result = updateVaccinePort.update(request);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse(
                VaccineResponse.class,
                result.getSuccess(),
                "Vacuna actualizada exitosamente");
    }

    /**
     * Delete a vaccine.
     *
     * @param id The vaccine id.
     * @return The deleted vaccine
     */
    @Operation(summary = "Delete a vaccine", description = "Delete a vaccine.", responses = {
            @ApiResponse(responseCode = "200", description = "Vaccine was deleted successfully", content = @Content(schema = @Schema(implementation = BasicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vaccine was not found.", content = @Content(schema = @Schema(implementation = FailureResponse.class))),
    })
    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Result<Void, VaccineFailure> result = deleteVaccinePort.deleteById(id);

        if (result.isFailure())
            return toFailureResponse(result.getFailure());

        return toOkResponse("Vacuna eliminada exitosamente");
    }
}
