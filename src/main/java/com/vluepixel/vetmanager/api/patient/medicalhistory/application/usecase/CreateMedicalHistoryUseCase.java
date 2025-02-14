package com.vluepixel.vetmanager.api.patient.medicalhistory.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.mapper.MedicalHistoryMapper;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.CreateMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.request.CreateMedicalHistoryRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create medical history use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateMedicalHistoryUseCase implements CreateMedicalHistoryPort {
    private final MedicalHistoryRepository medicalhistoryRepository;
    private final MedicalHistoryMapper medicalhistoryMapper;

    @Override
    @Transactional
    public MedicalHistoryDto create(CreateMedicalHistoryRequest request) {
        MDC.put("operationId", "Medical history name " + request.getName());
        log.info("Creating medical history");

        // Save the medicalhistory
        var newMedicalHistory = medicalhistoryMapper.fromRequest(request).build();
        newMedicalHistory = medicalhistoryRepository.save(newMedicalHistory);

        return medicalhistoryMapper.toDto(newMedicalHistory);
    }
}
