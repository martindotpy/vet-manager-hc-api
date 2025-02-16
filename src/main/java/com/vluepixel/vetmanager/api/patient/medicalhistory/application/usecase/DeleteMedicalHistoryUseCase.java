package com.vluepixel.vetmanager.api.patient.medicalhistory.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.DeleteMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete medical history use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteMedicalHistoryUseCase implements DeleteMedicalHistoryPort {
    private final MedicalHistoryRepository medicalHistoryRepository;

    @Override
    @Transactional
    public void deleteByPatientIdAndId(Long patientId, Long id) {
        MDC.put("operationId", "Medical history id " + id);
        log.info("Deleting medical history by id");

        int rowsModified = medicalHistoryRepository.updateBy(
                Criteria.of(
                        equal("id", id),
                        equal("patient.id", id)),
                FieldUpdate.set("deleted", true));

        // Verify any unexpected behavior
        if (rowsModified == 0) {
            log.error("Medical history with patient id '{}' and id '{}' not found",
                    fgBrightRed(patientId),
                    fgBrightRed(id));

            throw new NotFoundException(MedicalHistory.class, id);
        } else if (rowsModified > 1) {
            log.error("Medical history with patient id '{}' and id '{}' has more than one row modified",
                    fgBrightRed(patientId),
                    fgBrightRed(id));

            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Medical history with patient id '" +
                                    patientId +
                                    "' and id '" +
                                    id +
                                    "' has more than one row modified"));
        }

        log.info("Medical history deleted");
    }
}
