package com.vet.hc.api.patient.medicalhistory.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.medicalhistory.application.port.in.UpdateMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.domain.dto.MedicalHistoryDto;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.patient.medicalhistory.domain.payload.UpdateMedicalHistoryPayload;
import com.vet.hc.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a medical history.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateMedicalHistoryUseCase implements UpdateMedicalHistoryPort {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public Result<MedicalHistoryDto, MedicalHistoryFailure> update(UpdateMedicalHistoryPayload payload) {
        log.info("Updating medical history with id `{}`", payload.getId());

        MedicalHistory medicalHistoryToUpdate = MedicalHistory.builder()
                .id(payload.getId())
                .content(payload.getContent())
                .patient(Patient.builder().id(payload.getPatientId()).build())
                .build();

        var result = medicalHistoryRepository.save(medicalHistoryToUpdate);

        if (result.isFailure()) {
            log.error("Error updating medicalHistory: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                default -> Result.failure(MedicalHistoryFailure.UNEXPECTED);
            };
        }

        MedicalHistory medicalHistoryUpdated = result.getSuccess();

        log.info("MedicalHistory with id `{}` updated", medicalHistoryUpdated.getId());

        return Result.success(medicalHistoryMapper.toDto(medicalHistoryUpdated));
    }
}
