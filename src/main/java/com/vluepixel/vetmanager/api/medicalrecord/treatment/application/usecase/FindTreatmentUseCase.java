package com.vluepixel.vetmanager.api.medicalrecord.treatment.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightGreen;
import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import java.util.List;

import org.slf4j.MDC;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.dto.TreatmentDto;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.mapper.TreatmentMapper;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.application.port.in.FindTreatmentPort;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find treatment use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindTreatmentUseCase implements FindTreatmentPort {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;

    @Override
    public List<TreatmentDto> findAllByPatientIdAndMedicalRecordId(Long patientId, Long medicalRecordId) {
        MDC.put("operationId", "Treatment with medical record id " + medicalRecordId);
        log.info("Finding all treatment by patient id and medical record id");

        List<Treatment> treatments = treatmentRepository.findAllBy(
                equal("medicalRecord.patient.id", patientId),
                equal("medicalRecord.id", medicalRecordId));

        log.info("{} treatments found",
                fgBrightGreen(treatments.size()));

        return treatments.stream().map(treatmentMapper::toDto).toList();
    }

    @Override
    public TreatmentDto findById(Long id) {
        MDC.put("operationId", "Treatment id " + id);
        log.info("Finding treatment by id");

        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Treatment.class, id));

        log.info("Treatment found");

        return treatmentMapper.toDto(treatment);
    }
}
