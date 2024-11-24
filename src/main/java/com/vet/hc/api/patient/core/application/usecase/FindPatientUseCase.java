package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.query.PaginatedPatient;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an patient .
 */
@Slf4j
@NoArgsConstructor
public final class FindPatientUseCase implements FindPatientPort {
    private PatientRepository patientRepository;

    private final PatientMapper patientMapper = PatientMapper.INSTANCE;

    @Inject
    public FindPatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Result<PaginatedPatient, PatientFailure> match(Criteria criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'match'");
    }

    @Override
    public Result<PatientDto, PatientFailure> findById(Long id) {
        log.info("Finding patient by id: {}", id);

        var result = patientRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Patient not found with id: {}", id);

            return Result.failure(PatientFailure.NOT_FOUND);
        }

        log.info("Patient found: {}", result.get());

        return Result.success(patientMapper.toDto(result.get()));
    }
}
