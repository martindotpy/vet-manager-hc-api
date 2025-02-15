package com.vluepixel.vetmanager.api.patient.medicalhistory.application.usecase;

import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.mapper.MedicalHistoryMapper;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.UpdateMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.UpdateMedicalHistoryRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update medical history use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateMedicalHistoryUseCase implements UpdateMedicalHistoryPort {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    @Transactional
    public MedicalHistoryDto update(Long patientId, UpdateMedicalHistoryRequest request) {
        MDC.put("operationId", "Medical history id " + request.getId());
        log.info("Updating medical history");

        var medicalHistoryUpdated = medicalHistoryMapper.fromRequest(request).build();
        int rowsModified = medicalHistoryRepository.updateBy(
                Criteria.of(
                        equal("id", request.getId()),
                        equal("patientId", patientId)),
                FieldUpdate.set("content", request.getContent()));

        // Verify any unexpected behavior
        if (rowsModified == 0) {
            throw new NotFoundException(MedicalHistory.class, request.getId());
        } else if (rowsModified > 1) {
            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Medical history with patient id '" +
                                    patientId +
                                    "' and id '" +
                                    request.getId() +
                                    "' has more than one row modified"));
        }

        medicalHistoryUpdated = medicalHistoryRepository.findById(request.getId()).get();

        log.info("Medical history updated");

        return medicalHistoryMapper.toDto(medicalHistoryUpdated);
    }
}
