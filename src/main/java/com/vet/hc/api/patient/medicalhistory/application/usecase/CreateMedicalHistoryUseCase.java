package com.vet.hc.api.patient.medicalhistory.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.medicalhistory.application.port.in.CreateMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.patient.medicalhistory.domain.payload.CreateMedicalHistoryPayload;
import com.vet.hc.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a medical history.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateMedicalHistoryUseCase implements CreateMedicalHistoryPort {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public Result<MedicalHistoryDto, MedicalHistoryFailure> create(CreateMedicalHistoryPayload payload) {
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .content(payload.getContent())
                .patient(Patient.builder().id(payload.getPatientId()).build())
                .build();

        var result = medicalHistoryRepository.save(medicalHistory);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Unexpected error creating medical history with patient id `{}`", payload.getPatientId());

                    yield Result.failure(MedicalHistoryFailure.UNEXPECTED);
                }
            };
        }

        MedicalHistory createdMedicalHistory = result.getSuccess();

        log.info("MedicalHistory with patient id `{}` created", payload.getPatientId());

        return Result.success(medicalHistoryMapper.toDto(createdMedicalHistory));
    }
}
