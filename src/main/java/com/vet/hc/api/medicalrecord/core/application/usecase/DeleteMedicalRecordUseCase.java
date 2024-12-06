package com.vet.hc.api.medicalrecord.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.core.application.port.in.DeleteMedicalRecordPort;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a medical record.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteMedicalRecordUseCase implements DeleteMedicalRecordPort {
    private final MedicalRecordRepository medicalrecordRepository;

    @Override
    public Result<Void, MedicalRecordFailure> deleteById(Long id) {
        log.info("Deleting medical record with id: {}", id);

        var result = medicalrecordRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("MedicalRecord with id {} not found", id);

                    yield Result.failure(MedicalRecordFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting medical record with id {}", id);

                    yield Result.failure(MedicalRecordFailure.UNEXPECTED);
                }
            };
        }

        log.info("MedicalRecord with id {} deleted", id);

        return Result.success();
    }
}
