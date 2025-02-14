package com.vluepixel.vetmanager.api.patient.medicalhistory.application.usecase;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.mapper.MedicalHistoryMapper;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.UpdateMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.UpdateMedicalHistoryRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update medical history use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateMedicalHistoryUseCase implements UpdateMedicalHistoryPort {
    private final MedicalHistoryRepository medicalhistoryRepository;
    private final MedicalHistoryMapper medicalhistoryMapper;

    @Override
    @Transactional
    public MedicalHistoryDto update(UpdateMedicalHistoryRequest request) {
        MDC.put("operationId", "Medical history id " + request.getId());
        log.info("Updating medical history");

        // Update the medicalhistory
        var medicalhistoryUpdated = medicalhistoryMapper.fromRequest(request).build();
        medicalhistoryUpdated = medicalhistoryRepository.save(medicalhistoryUpdated);

        return medicalhistoryMapper.toDto(medicalhistoryUpdated);
    }
}
