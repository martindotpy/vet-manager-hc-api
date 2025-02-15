package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.usecase;

import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.mapper.TreatmentMapper;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.UpdateTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.request.UpdateTreatmentRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update treatment use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateTreatmentUseCase implements UpdateTreatmentPort {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;

    @Override
    @Transactional
    public TreatmentDto update(Long patientId, Long medicalRecordId, UpdateTreatmentRequest request) {
        MDC.put("operationId", "Treatment id " + request.getId());
        log.info("Updating treatment");

        Treatment treatmentUpdated = treatmentMapper.fromRequest(request).build();
        int rowsModified = treatmentRepository.updateBy(
                Criteria.of(
                        equal("id", request.getId()),
                        equal("medicalRecord.id", medicalRecordId),
                        equal("medicalRecord.patient.id", patientId)),
                FieldUpdate.set("description", treatmentUpdated.getDescription()),
                FieldUpdate.set("order", treatmentUpdated.getOrder()));

        // Verify any unexpected behavior
        if (rowsModified == 0) {
            throw new NotFoundException(Treatment.class, request.getId());
        } else if (rowsModified > 1) {
            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Treatment with patient id '" +
                                    patientId +
                                    "', medical record id '" +
                                    medicalRecordId +
                                    "' and id '" +
                                    request.getId() +
                                    "' has more than one row modified"));
        }

        treatmentUpdated = treatmentRepository.findById(request.getId()).get();

        log.info("Treatment updated");

        return treatmentMapper.toDto(treatmentUpdated);
    }
}
