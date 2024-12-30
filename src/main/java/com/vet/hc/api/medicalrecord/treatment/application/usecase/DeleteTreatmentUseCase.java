package com.vet.hc.api.medicalrecord.treatment.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.medicalrecord.treatment.application.port.in.DeleteTreatmentPort;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a treatment.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteTreatmentUseCase implements DeleteTreatmentPort {
    private final TreatmentRepository medicalrecordRepository;

    @Override
    public Result<Void, TreatmentFailure> deleteById(Long id) {
        log.info("Deleting treatment with id: {}", id);

        var result = medicalrecordRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(TreatmentFailure.UNEXPECTED);
        }

        log.info("Treatment with id {} deleted", id);

        return Result.success();
    }
}
