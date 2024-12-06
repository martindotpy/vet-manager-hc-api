package com.vet.hc.api.patient.medicalhistory.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.repository.MedicalHistoryHibernateRepository;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist medical histories in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class MedicalHistoryPersistenceAdapter implements MedicalHistoryRepository {
    private final MedicalHistoryHibernateRepository medicalHistoryHibernateRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;
    private final RepositoryFailureMapper repositoryFailureMapper;

    @Override
    public Optional<MedicalHistory> findById(Long id) {
        return medicalHistoryHibernateRepository.findById(id)
                .map(medicalHistoryMapper::toDomain);
    }

    @Override
    public Result<MedicalHistory, RepositoryFailure> save(MedicalHistory medicalHistory) {
        try {
            return Result.success(
                    medicalHistoryMapper.toDomain(
                            medicalHistoryHibernateRepository.save(medicalHistoryMapper.toEntity(medicalHistory))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, medicalHistory));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, medicalHistory));

            log.error("Error saving medicalHistory: {}", medicalHistory, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, medicalHistory));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, medicalHistory));

            log.error("Error saving medicalHistory: {}", medicalHistory, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            medicalHistoryHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("MedicalHistory with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting medical history with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e       The exception.
     * @param medical history The medical history.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            MedicalHistory medicalHistory) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving medical history with id `{}`", medicalHistory.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e       The exception.
     * @param medical history The medical history.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            MedicalHistory medicalHistory) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("MedicalHistory with id `{}` not found", medicalHistory.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("MedicalHistory with id `{}` not found", medicalHistory.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving medicalHistory", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
