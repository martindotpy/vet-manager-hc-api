package com.vet.hc.api.patient.core.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.patient.core.adapter.out.persistence.repository.PatientHibernateRepository;
import com.vet.hc.api.patient.core.application.mapper.PatientMapper;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.core.domain.repository.PatientRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for patient persistence.
 */
@Slf4j
@NoArgsConstructor
public class PatientPersistenceAdapter implements PatientRepository {
    private PatientHibernateRepository patientHibernateRepository;

    private PatientMapper patientMapper = PatientMapper.INSTANCE;

    @Inject
    public PatientPersistenceAdapter(PatientHibernateRepository patientHibernateRepository) {
        this.patientHibernateRepository = patientHibernateRepository;
    }

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
    public Result<Paginated<Patient>, RepositoryFailure> match(Criteria criteria) {
        return null;
        // try {
        // var response = patientHibernateRepository.match(criteria);

        // return Result.success(
        // Paginated.<Patient>builder()
        // .content(response.getContent().stream()
        // .map(patientMapper::toDomain)
        // .toList())
        // .page(response.getPage())
        // .size(response.getSize())
        // .totalElements(response.getTotalElements())
        // .totalPages(response.getTotalPages())
        // .build());
        // } catch (IllegalArgumentException e) {
        // log.warn("Field not found in criteria: {}", e.getMessage());
        // return Result.failure(RepositoryFailure.FIELD_NOT_FOUND);
        // } catch (Exception e) {
        // log.error("Unexpected error: {}", e.getMessage());
        // return Result.failure(RepositoryFailure.UNEXPECTED);
        // }
    }

    @Override
    public Result<Patient, RepositoryFailure> save(Patient patient) {
        try {
            return Result.success(patientMapper
                    .toDomain(patientHibernateRepository.save(patientMapper.toEntity(patient))));
        } catch (Exception e) {
            log.error("Error saving patient with name `{}`", patient.getName(), e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            patientHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Patient with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting patient with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }
}
