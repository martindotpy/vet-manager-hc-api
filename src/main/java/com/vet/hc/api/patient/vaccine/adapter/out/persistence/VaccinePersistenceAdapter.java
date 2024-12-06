package com.vet.hc.api.patient.vaccine.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;
import com.vet.hc.api.patient.vaccine.adapter.out.persistence.repository.VaccineHibernateRepository;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.patient.vaccine.domain.repository.VaccineRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist vaccines in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class VaccinePersistenceAdapter implements VaccineRepository {
    private final VaccineHibernateRepository vaccineHibernateRepository;
    private final VaccineMapper vaccineMapper;
    private final RepositoryFailureMapper repositoryFailureMapper;

    @Override
    public Optional<Vaccine> findById(Long id) {
        return vaccineHibernateRepository.findById(id)
                .map(vaccineMapper::toDomain);
    }

    @Override
    public Result<Vaccine, RepositoryFailure> save(Vaccine vaccine) {
        try {
            return Result.success(
                    vaccineMapper.toDomain(vaccineHibernateRepository.save(vaccineMapper.toEntity(vaccine))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, vaccine));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, vaccine));

            log.error("Error saving vaccine: {}", vaccine, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, vaccine));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, vaccine));

            log.error("Error saving vaccine: {}", vaccine, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            vaccineHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Vaccine with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting vaccine with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e       The exception.
     * @param vaccine The vaccine.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            Vaccine vaccine) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving vaccine with id `{}`", vaccine.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e       The exception.
     * @param vaccine The vaccine.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            Vaccine vaccine) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("Vaccine with id `{}` not found", vaccine.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("Vaccine with id `{}` not found", vaccine.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving vaccine", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
