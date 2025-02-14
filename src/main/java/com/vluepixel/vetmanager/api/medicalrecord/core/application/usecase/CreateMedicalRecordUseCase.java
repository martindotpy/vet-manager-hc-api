package com.vluepixel.vetmanager.api.medicalrecord.core.application.usecase;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.mapper.MedicalRecordMapper;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.CreateMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.CreateMedicalRecordRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create medical record use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateMedicalRecordUseCase implements CreateMedicalRecordPort {
    private final MedicalRecordRepository medicalrecordRepository;
    private final MedicalRecordMapper medicalrecordMapper;

    @Override
    @Transactional
    public MedicalRecordDto create(CreateMedicalRecordRequest request) {
        MDC.put("operationId", "Medical record with patient id " + request.getPatientId());
        log.info("Creating medical record");

        // Save the medical record
        var newMedicalRecord = medicalrecordMapper.fromRequest(request).build();
        newMedicalRecord = medicalrecordRepository.save(newMedicalRecord);

        return medicalrecordMapper.toDto(newMedicalRecord);
    }
}
