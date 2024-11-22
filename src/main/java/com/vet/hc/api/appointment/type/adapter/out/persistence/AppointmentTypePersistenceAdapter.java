package com.vet.hc.api.appointment.type.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.appointment.type.adapter.out.mapper.AppointmentTypeMapper;
import com.vet.hc.api.appointment.type.adapter.out.persistence.repository.AppointmentTypeHibernateRepository;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;
import com.vet.hc.api.appointment.type.domain.repository.AppointmentTypeRepository;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist appointment types in the database.
 */
@Slf4j
@NoArgsConstructor
public final class AppointmentTypePersistenceAdapter implements AppointmentTypeRepository {
    private AppointmentTypeHibernateRepository appointmetTypeHibernateRepository;

    private final AppointmentTypeMapper appointmentTypeMapper = AppointmentTypeMapper.INSTANCE;

    @Inject
    public AppointmentTypePersistenceAdapter(AppointmentTypeHibernateRepository appointmetTypeHibernateRepository) {
        this.appointmetTypeHibernateRepository = appointmetTypeHibernateRepository;
    }

    @Override
    public List<AppointmentType> findAll() {
        return appointmetTypeHibernateRepository.findAll().stream()
                .map(appointmentTypeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AppointmentType> findById(Long id) {
        return appointmetTypeHibernateRepository.findById(id)
                .map(appointmentTypeMapper::toDomain);
    }

    @Override
    public Result<AppointmentType, RepositoryFailure> save(AppointmentType appointmentType) {
        try {
            return Result.success(appointmentTypeMapper
                    .toDomain(appointmetTypeHibernateRepository.save(appointmentTypeMapper.toEntity(appointmentType))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, appointmentType));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, appointmentType));

            log.error("Error saving appointment type: {}", appointmentType, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, appointmentType));

            log.error("Error saving appointment type: {}", appointmentType, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            appointmetTypeHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment type with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment type with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e               The exception.
     * @param appointmentType The appointment type.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            AppointmentType appointmentType) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        if (mySqlFailure == MySQLRepositoryFailure.DUPLICATED) {
            RepositoryFailure repositoryFailure = RepositoryFailure.DUPLICATED;

            if (e.getConstraintName().equals("UK_APPOINTMENT_TYPE_NAME")) {
                log.error("The appointment type with name `{}` already exists", appointmentType.getName());

                repositoryFailure.setField("name");
            } else {
                log.error("Error saving appointment type: {}", appointmentType, e);
                repositoryFailure = RepositoryFailure.UNEXPECTED;
            }

            return repositoryFailure;
        }

        log.error("Error saving appointment type with name `{}`", appointmentType.getName(), e);

        return RepositoryFailure.UNEXPECTED;
    }
}
