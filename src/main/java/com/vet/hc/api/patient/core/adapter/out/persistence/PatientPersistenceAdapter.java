package com.vet.hc.api.patient.core.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.core.adapter.out.persistence.repository.PatientHibernateRepository;
import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.domain.failure.PatientFailure;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for patient persistence.
 */
@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public final class PatientPersistenceAdapter implements PatientRepository {
    private final PatientHibernateRepository patientHibernateRepository;
    private final PatientMapper patientMapper;

    @Override
    public List<Patient> findAll() {
        return patientHibernateRepository.findAll().stream()
                .map(patientMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientHibernateRepository.findById(id)
                .map(patientMapper::toDomain);
    }

    @Override
    public Result<Paginated<Patient>, PatientFailure> match(Criteria criteria) {
        try {
            var response = patientHibernateRepository.match(criteria);

            return Result.success(
                    Paginated.<Patient>builder()
                            .content(response.getContent().stream()
                                    .map(patientMapper::toDomain)
                                    .toList())
                            .page(response.getPage())
                            .size(response.getSize())
                            .totalElements(response.getTotalElements())
                            .totalPages(response.getTotalPages())
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Field not found in criteria: {}", e.getMessage());
            return Result.failure(PatientFailure.FIELD_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(PatientFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Patient, PatientFailure> save(Patient patient) {
        try {
            return Result.success(patientMapper
                    .toDomain(patientHibernateRepository.save(patientMapper.toEntity(patient))));
        } catch (Exception e) {
            log.error("Error saving patient with name `{}`", patient.getName(), e);

            return Result.failure(PatientFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, PatientFailure> deleteById(Long id) {
        try {
            patientHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Patient with id {} not found", id);

            return Result.failure(PatientFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting patient with id: {}", id, e);

            return Result.failure(PatientFailure.UNEXPECTED);
        }
    }
}
