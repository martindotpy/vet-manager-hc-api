package com.vet.hc.api.patient.medicalhistory.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.medicalhistory.application.port.in.DeleteMedicalHistoryPort;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a medical history.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteMedicalHistoryUseCase implements DeleteMedicalHistoryPort {
    private final MedicalHistoryRepository medicalhistoryRepository;

    @Override
    public Result<Void, MedicalHistoryFailure> deleteById(Long id) {
        log.info("Deleting medical history with id: {}", id);

        var result = medicalhistoryRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("MedicalHistory with id {} not found", id);

                    yield Result.failure(MedicalHistoryFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting medical history with id {}", id);

                    yield Result.failure(MedicalHistoryFailure.UNEXPECTED);
                }
            };
        }

        log.info("MedicalHistory with id {} deleted", id);

        return Result.success();
    }
}
