package com.vluepixel.vetmanager.api.medicalrecord.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import java.util.List;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.mapper.MedicalRecordMapper;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.FindMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find medical record.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindMedicalRecordUseCase implements FindMedicalRecordPort {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public List<MedicalRecordDto> findAllByPatientId(Long patientId) {
        MDC.put("operationId", "Medical record of patient with id " + patientId);
        log.info("Finding all medical record");

        var medicalRecords = medicalRecordRepository.findAllBy(like("patient.id", patientId));

        log.info("Retrieved {} medical record ",
                fgBrightGreen(medicalRecords.size()));

        return medicalRecords.stream().map(medicalRecordMapper::toDto).toList();
    }

    @Override
    public MedicalRecordDto findById(Long id) {
        MDC.put("operationId", "Medical record id " + id);
        log.info("Finding medical record by id");

        var medicalrecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MedicalRecord.class, id));

        log.info("Retrieved medical record {}",
                fgBrightGreen(medicalrecord));

        return medicalRecordMapper.toDto(medicalrecord);
    }
}
