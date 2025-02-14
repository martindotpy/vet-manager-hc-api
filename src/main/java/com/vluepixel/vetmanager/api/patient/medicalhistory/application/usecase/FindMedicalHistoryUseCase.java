package com.vluepixel.vetmanager.api.patient.medicalhistory.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.like;

import java.util.List;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.patient.medicalhistory.application.dto.MedicalHistoryDto;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.mapper.MedicalHistoryMapper;
import com.vluepixel.vetmanager.api.patient.medicalhistory.application.port.in.FindMedicalHistoryPort;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find medical history.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindMedicalHistoryUseCase implements FindMedicalHistoryPort {
    private final MedicalHistoryRepository medicalhistoryRepository;
    private final MedicalHistoryMapper medicalhistoryMapper;

    @Override
    public List<MedicalHistoryDto> findAllByPatientId(Long patientId) {
        MDC.put("operationId", "Medical history of patient with id " + patientId);
        log.info("Finding all medical history");

        var medicalHistories = medicalhistoryRepository.findAllBy(like("patient.id", patientId));

        log.info("Retrieved {} medical history ",
                fgBrightGreen(medicalHistories.size()));

        return medicalHistories.stream().map(medicalhistoryMapper::toDto).toList();
    }

    @Override
    public MedicalHistoryDto findById(Long id) {
        MDC.put("operationId", "Medical history id " + id);
        log.info("Finding medical history by id");

        var medicalhistory = medicalhistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MedicalHistory.class, id));

        log.info("Retrieved medical history {}",
                fgBrightGreen(medicalhistory));

        return medicalhistoryMapper.toDto(medicalhistory);
    }
}
