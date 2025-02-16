package com.vluepixel.vetmanager.api.vaccine.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;
import com.vluepixel.vetmanager.api.vaccine.core.application.port.in.DeleteVaccinePort;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;
import com.vluepixel.vetmanager.api.vaccine.core.domain.repository.VaccineRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Delete vaccine use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class DeleteVaccineUseCase implements DeleteVaccinePort {
    private final VaccineRepository vaccineRepository;

    @Override
    @Transactional
    public void deleteByPatientIdAndId(Long patientId, Long id) {
        MDC.put("operationId", "Vaccine id " + id);
        log.info("Deleting vaccine by id");

        int rowsModified = vaccineRepository.updateBy(
                Criteria.of(
                        equal("id", id),
                        equal("patient.id", id)),
                FieldUpdate.set("deleted", true));

        // Verify any unexpected behavior
        if (rowsModified == 0) {
            log.error("Vaccine with patient id '{}' and id '{}' not found",
                    fgBrightRed(patientId),
                    fgBrightRed(id));

            throw new NotFoundException(Vaccine.class, id);
        } else if (rowsModified > 1) {
            log.error("Vaccine with patient id '{}' and id '{}' has more than one row modified",
                    fgBrightRed(patientId),
                    fgBrightRed(id));

            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Vaccine with patient id '" +
                                    patientId +
                                    "' and id '" +
                                    id +
                                    "' has more than one row modified"));
        }

        log.info("Vaccine deleted");
    }
}
