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
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    @Transactional
    public MedicalHistoryDto create(CreateMedicalHistoryRequest request) {
        MDC.put("operationId", "Medical history with patient id " + request.getPatientId());
        log.info("Creating medical history");

        // Save the medical history
        var newMedicalHistory = medicalHistoryMapper.fromRequest(request).build();
        newMedicalHistory = medicalHistoryRepository.save(newMedicalHistory);

        return medicalHistoryMapper.toDto(newMedicalHistory);
    }
}
