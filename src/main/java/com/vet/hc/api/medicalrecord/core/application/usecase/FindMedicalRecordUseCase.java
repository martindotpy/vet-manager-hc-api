package com.vet.hc.api.medicalrecord.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.medicalrecord.core.application.port.in.FindMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.dto.MedicalRecordDto;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a medical record.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindMedicalRecordUseCase implements FindMedicalRecordPort {
    private final MedicalRecordRepository medicalrecordRepository;
    private final MedicalRecordMapper medicalrecordMapper;

    @Override
    public Result<MedicalRecordDto, MedicalRecordFailure> findById(Long id) {
        log.info("Finding medical record by id `{}`", id);

        var result = medicalrecordRepository.findById(id);

        if (result.isEmpty()) {
            log.error("MedicalRecord not found with id `{}`", id);

            return Result.failure(MedicalRecordFailure.NOT_FOUND);
        }

        MedicalRecord medicalRecord = result.get();

        log.info("MedicalRecord with id `{}` found`", medicalRecord.getId());

        return Result.success(medicalrecordMapper.toDto(medicalRecord));
    }
}
