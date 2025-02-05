package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.core.application.port.in.DeletePatientPort;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an patient .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeletePatientUseCase implements DeletePatientPort {
    private final PatientRepository patientRepository;

    @Override
    public Result<Void, PatientFailure> deleteById(Long id) {
        log.info("Deleting patient with id: {}", id);

        var result = patientRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(PatientFailure.UNEXPECTED);
        }

        log.info("Patient with id {} deleted", id);

        return Result.success();
    }
}
