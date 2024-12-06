package com.vet.hc.api.bill.treatmentsale.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.bill.treatmentsale.adapter.out.mapper.TreatmentSaleMapper;
import com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.repository.TreatmentSaleHibernateRepository;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist treatment sales in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class TreatmentSalePersistenceAdapter implements TreatmentSaleRepository {
    private final TreatmentSaleHibernateRepository treatmentSaleHibernateRepository;
    private final TreatmentSaleMapper treatmentSaleMapper;
    private final RepositoryFailureMapper repositoryFailureMapper;

    @Override
    public Optional<TreatmentSale> findById(Long id) {
        return treatmentSaleHibernateRepository.findById(id)
                .map(treatmentSaleMapper::toDomain);
    }

    @Override
    public Result<TreatmentSale, RepositoryFailure> save(TreatmentSale treatmentSale) {
        try {
            return Result.success(
                    treatmentSaleMapper.toDomain(
                            treatmentSaleHibernateRepository.save(treatmentSaleMapper.toEntity(treatmentSale))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, treatmentSale));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, treatmentSale));

            log.error("Error saving treatment sale: {}", treatmentSale, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, treatmentSale));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, treatmentSale));

            log.error("Error saving treatment sale: {}", treatmentSale, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            treatmentSaleHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Treatment sale with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting treatment sale with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e             The exception.
     * @param treatmentSale The treatment sale.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            TreatmentSale treatmentSale) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving treatment sale with id `{}`", treatmentSale.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e             The exception.
     * @param treatmentSale The treatment sale.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            TreatmentSale treatmentSale) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("Treatment sale with id `{}` not found", treatmentSale.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("Treatment sale with id `{}` not found", treatmentSale.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving treatment sale", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
