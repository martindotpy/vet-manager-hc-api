package com.vet.hc.api.medicalrecord.treatment.application.usecase;

import com.vet.hc.api.medicalrecord.treatment.application.port.in.DeleteTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a treatment.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteTreatmentUseCase implements DeleteTreatmentPort {
    private TreatmentRepository medicalrecordRepository;

    @Inject
    public DeleteTreatmentUseCase(TreatmentRepository medicalrecordRepository) {
        this.medicalrecordRepository = medicalrecordRepository;
    }

    @Override
    public Result<Void, TreatmentFailure> deleteById(Long id) {
        log.info("Deleting treatment with id: {}", id);

        var result = medicalrecordRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("Treatment with id {} not found", id);

                    yield Result.failure(TreatmentFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting treatment with id {}", id);

                    yield Result.failure(TreatmentFailure.UNEXPECTED);
                }
            };
        }

        log.info("Treatment with id {} deleted", id);

        return Result.success();
    }
}