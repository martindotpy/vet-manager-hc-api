package com.vet.hc.api.bill.core.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.bill.core.adapter.out.mapper.BillMapper;
import com.vet.hc.api.bill.core.adapter.out.persistence.repository.BillHibernateRepository;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
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
public final class BillPersistenceAdapter implements BillRepository {
    private BillHibernateRepository appointmetTypeHibernateRepository;

    private final BillMapper billMapper = BillMapper.INSTANCE;

    @Inject
    public BillPersistenceAdapter(BillHibernateRepository appointmetTypeHibernateRepository) {
        this.appointmetTypeHibernateRepository = appointmetTypeHibernateRepository;
    }

    @Override
    public List<Bill> findAll() {
        return appointmetTypeHibernateRepository.findAll().stream()
                .map(billMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return appointmetTypeHibernateRepository.findById(id)
                .map(billMapper::toDomain);
    }

    @Override
    public Result<Bill, RepositoryFailure> save(Bill bill) {
        try {
            return Result.success(billMapper
                    .toDomain(appointmetTypeHibernateRepository.save(billMapper.toEntity(bill))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, bill));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, bill));

            log.error("Error saving appointment type: {}", bill, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, bill));

            log.error("Error saving appointment type: {}", bill, e);

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
     * @param e    The exception.
     * @param bill The appointment type.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            Bill bill) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        if (mySqlFailure == MySQLRepositoryFailure.DUPLICATED) {
            RepositoryFailure repositoryFailure = RepositoryFailure.DUPLICATED;

            if (e.getConstraintName().equals("UK_APPOINTMENT_TYPE_NAME")) {
                log.error("The appointment type with name `{}` already exists", bill.getName());

                repositoryFailure.setField("name");
            } else {
                log.error("Error saving appointment type: {}", bill, e);
                repositoryFailure = RepositoryFailure.UNEXPECTED;
            }

            return repositoryFailure;
        }

        log.error("Error saving appointment type with name `{}`", bill.getName(), e);

        return RepositoryFailure.UNEXPECTED;
    }
}
