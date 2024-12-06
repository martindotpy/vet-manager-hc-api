package com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.repository.TreatmentHibernateRepository;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist treatments in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class TreatmentPersistenceAdapter implements TreatmentRepository {
    private final TreatmentHibernateRepository treatmentHibernateRepository;
    private final TreatmentMapper treatmentMapper;
    private final RepositoryFailureMapper repositoryFailureMapper;

    @Override
    public Optional<Treatment> findById(Long id) {
        return treatmentHibernateRepository.findById(id)
                .map(treatmentMapper::toDomain);
    }

    @Override
    public Result<Treatment, RepositoryFailure> save(Treatment treatment) {
        try {
            return Result.success(
                    treatmentMapper.toDomain(
                            treatmentHibernateRepository.save(treatmentMapper.toEntity(treatment))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, treatment));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, treatment));

            log.error("Error saving treatment: {}", treatment, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, treatment));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, treatment));

            log.error("Error saving treatment: {}", treatment, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            treatmentHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Treatment with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting treatment with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e         The exception.
     * @param treatment The treatment.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            Treatment treatment) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving treatment with id `{}`", treatment.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e         The exception.
     * @param treatment The treatment.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            Treatment treatment) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("Treatment with id `{}` not found", treatment.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("Treatment with id `{}` not found", treatment.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving treatment", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
