package com.vet.hc.api.bill.appointmentsale.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.bill.appointmentsale.adapter.out.mapper.AppointmentSaleMapper;
import com.vet.hc.api.bill.appointmentsale.adapter.out.persistence.repository.AppointmentSaleHibernateRepository;
import com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale;
import com.vet.hc.api.bill.appointmentsale.domain.repository.AppointmentSaleRepository;
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
 * Adapter to persist appointment sales in the database.
 */
@Slf4j
@NoArgsConstructor
public final class AppointmentSalePersistenceAdapter implements AppointmentSaleRepository {
    private AppointmentSaleHibernateRepository appointmentSaleHibernateRepository;

    private final AppointmentSaleMapper appointmentSaleMapper = AppointmentSaleMapper.INSTANCE;
    private final RepositoryFailureMapper repositoryFailureMapper = RepositoryFailureMapper.INSTANCE;

    @Inject
    public AppointmentSalePersistenceAdapter(
            AppointmentSaleHibernateRepository appointmentSaleHibernateRepository) {
        this.appointmentSaleHibernateRepository = appointmentSaleHibernateRepository;
    }

    @Override
    public Optional<AppointmentSale> findById(Long id) {
        return appointmentSaleHibernateRepository.findById(id)
                .map(appointmentSaleMapper::toDomain);
    }

    @Override
    public Result<AppointmentSale, RepositoryFailure> save(AppointmentSale appointmentSale) {
        try {
            return Result.success(
                    appointmentSaleMapper.toDomain(
                            appointmentSaleHibernateRepository.save(appointmentSaleMapper.toEntity(appointmentSale))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, appointmentSale));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, appointmentSale));

            log.error("Error saving appointment sale: {}", appointmentSale, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, appointmentSale));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, appointmentSale));

            log.error("Error saving appointment sale: {}", appointmentSale, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            appointmentSaleHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Appointment sale with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting appointment sale with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e               The exception.
     * @param appointmentSale The appointment sale.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            AppointmentSale appointmentSale) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving appointment sale with id `{}`", appointmentSale.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e               The exception.
     * @param appointmentSale The appointment sale.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            AppointmentSale appointmentSale) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("Appointment sale with id `{}` not found", appointmentSale.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("Appointment sale with id `{}` not found", appointmentSale.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving appointment sale", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
