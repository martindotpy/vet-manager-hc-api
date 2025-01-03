package com.vet.hc.api.patient.medicalhistory.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.medicalhistory.application.port.in.FindMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a medical history.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindMedicalHistoryUseCase implements FindMedicalHistoryPort {
    private final MedicalHistoryRepository medicalhistoryRepository;
    private final MedicalHistoryMapper medicalhistoryMapper;

    @Override
    public Result<MedicalHistoryDto, MedicalHistoryFailure> findById(Long id) {
        log.info("Finding medical history by id `{}`", id);

        var result = medicalhistoryRepository.findById(id);

        if (result.isEmpty()) {
            log.error("MedicalHistory not found with id `{}`", id);

            return Result.failure(MedicalHistoryFailure.NOT_FOUND);
        }

        MedicalHistory medicalHistory = result.get();

        log.info("MedicalHistory with id `{}` found`", medicalHistory.getId());

        return Result.success(medicalhistoryMapper.toDto(medicalHistory));
    }
}
