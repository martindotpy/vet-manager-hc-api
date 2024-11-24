package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.patient.core.application.port.in.DeletePatientPort;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete an patient .
 */
@Slf4j
@NoArgsConstructor
public final class DeletePatientUseCase implements DeletePatientPort {
    private PatientRepository patientRepository;

    @Inject
    public DeletePatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Result<Void, PatientFailure> deleteById(Long id) {
        log.info("Deleting patient with id: {}", id);

        var result = patientRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            if (repositoryFailure == RepositoryFailure.NOT_FOUND) {
                log.error("Patient with id {} not found", id);

                return Result.failure(PatientFailure.NOT_FOUND);
            }

            log.error("Error deleting patient with id {}", id, repositoryFailure);

            return Result.failure(PatientFailure.UNEXPECTED);
        }

        log.info("Patient with id {} deleted", id);

        return Result.success();
    }
}
