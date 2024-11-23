package com.vet.hc.api.appointment.details.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.appointment.details.adapter.out.mapper.AppointmentDetailsMapper;
import com.vet.hc.api.appointment.details.adapter.out.persistence.repository.AppointmentDetailsHibernateRepository;
import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.appointment.details.domain.repository.AppointmentDetailsRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist appointment detailss in the database.
 */
@Slf4j
@NoArgsConstructor
public final class AppointmentDetailsPersistenceAdapter implements AppointmentDetailsRepository {
    private AppointmentDetailsHibernateRepository appointmetDetailsHibernateRepository;

    private final AppointmentDetailsMapper appointmentDetailsMapper = AppointmentDetailsMapper.INSTANCE;
    private final RepositoryFailureMapper repositoryFailureMapper = RepositoryFailureMapper.INSTANCE;

    @Inject
    public AppointmentDetailsPersistenceAdapter(
            AppointmentDetailsHibernateRepository appointmetDetailsHibernateRepository) {
        this.appointmetDetailsHibernateRepository = appointmetDetailsHibernateRepository;
    }

    @Override
    public Optional<AppointmentDetails> findById(Long id) {
        return appointmetDetailsHibernateRepository.findById(id)
                .map(appointmentDetailsMapper::toDomain);
    }

    @Override
    public Result<AppointmentDetails, RepositoryFailure> save(AppointmentDetails appointmentDetails) {
        try {
            return Result.success(appointmentDetailsMapper
                    .toDomain(appointmetDetailsHibernateRepository
                            .save(appointmentDetailsMapper.toEntity(appointmentDetails))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, appointmentDetails));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, appointmentDetails));

            log.error("Error saving appointment details: {}", appointmentDetails, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, appointmentDetails));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, appointmentDetails));

            log.error("Error saving appointment details: {}", appointmentDetails, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            appointmetDetailsHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment details with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment details with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e                  The exception.
     * @param appointmentDetails The appointment details.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            AppointmentDetails appointmentDetails) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving appointment details with id `{}`", appointmentDetails.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e                  The exception.
     * @param appointmentDetails The appointment details.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            AppointmentDetails appointmentDetails) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("Appointment details with id `{}` not found", appointmentDetails.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("Appointment details with id `{}` not found", appointmentDetails.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving appointment details", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
