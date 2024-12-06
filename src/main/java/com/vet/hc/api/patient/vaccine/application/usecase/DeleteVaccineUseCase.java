package com.vet.hc.api.patient.vaccine.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.vaccine.application.port.in.DeleteVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.repository.VaccineRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a vaccine.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteVaccineUseCase implements DeleteVaccinePort {
    private final VaccineRepository vaccineRepository;

    @Override
    public Result<Void, VaccineFailure> deleteById(Long id) {
        log.info("Deleting vaccine with id: {}", id);

        var result = vaccineRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("Vaccine with id {} not found", id);

                    yield Result.failure(VaccineFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting vaccine with id {}", id);

                    yield Result.failure(VaccineFailure.UNEXPECTED);
                }
            };
        }

        log.info("Vaccine with id {} deleted", id);

        return Result.success();
    }
}
