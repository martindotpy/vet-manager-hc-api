package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.DeleteTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete treatment use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteTreatmentUseCase implements DeleteTreatmentPort {
    private final TreatmentRepository treatmentRepository;

    @Override
    @Transactional
    public void deleteByPatientIdAndMedicalRecordIdAndId(Long patientId, Long medicalRecordId, Long id) {
        MDC.put("operationId", "Treatment id " + id);
        log.info("Deleting treatment by id");

        int rowsModified = treatmentRepository.updateBy(
                Criteria.of(
                        equal("id", id),
                        equal("medicalRecord.id", medicalRecordId),
                        equal("medicalRecord.patient.id", patientId)),
                FieldUpdate.set("deleted", true));

        // Verify any unexpected behavior
        if (rowsModified == 0) {
            log.error("Treatment with patient id '{}', medical record id '{}' and id '{}' not found",
                    fgBrightRed(patientId),
                    fgBrightRed(medicalRecordId),
                    fgBrightRed(id));

            throw new NotFoundException(Treatment.class, id);
        } else if (rowsModified > 1) {
            log.error(
                    "Treatment with patient id '{}', medical record id '{}' and id '{}' has more than one row modified",
                    fgBrightRed(patientId),
                    fgBrightRed(medicalRecordId),
                    fgBrightRed(id));

            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Treatment with patient id '" +
                                    patientId +
                                    "', medical record id '" +
                                    medicalRecordId +
                                    "' and id '" +
                                    id +
                                    "' has more than one row modified"));
        }

        log.info("Treatment deleted");
    }
}
