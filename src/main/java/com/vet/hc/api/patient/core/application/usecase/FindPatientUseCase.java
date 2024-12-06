package com.vet.hc.api.patient.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.application.port.in.FindPatientPort;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.query.PaginatedPatient;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find an patient .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindPatientUseCase implements FindPatientPort {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public Result<PaginatedPatient, PatientFailure> match(Criteria criteria) {
        log.info("Finding patients by criteria: {}", criteria);

        var result = patientRepository.match(criteria);

        if (result.isFailure()) {
            log.error("Error finding patients by criteria: {}", criteria);

            return Result.failure(PatientFailure.UNEXPECTED);
        }

        log.info("Patients found by criteria: {}", result.getSuccess().getSize());

        return Result.success(PaginatedPatient.builder()
                .content(result.getSuccess().getContent().stream()
                        .map(patientMapper::toDto)
                        .toList())
                .page(result.getSuccess().getPage())
                .size(result.getSuccess().getSize())
                .totalElements(result.getSuccess().getTotalElements())
                .totalPages(result.getSuccess().getTotalPages())
                .build());
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
