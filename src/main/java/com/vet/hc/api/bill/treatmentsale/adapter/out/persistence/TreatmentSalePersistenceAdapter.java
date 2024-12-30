package com.vet.hc.api.bill.treatmentsale.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.bill.treatmentsale.adapter.out.mapper.TreatmentSaleMapper;
import com.vet.hc.api.bill.treatmentsale.adapter.out.persistence.repository.TreatmentSaleHibernateRepository;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

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

    @Override
    public Optional<TreatmentSale> findById(Long id) {
        return treatmentSaleHibernateRepository.findById(id)
                .map(treatmentSaleMapper::toDomain);
    }

    @Override
    public Result<TreatmentSale, TreatmentSaleFailure> save(TreatmentSale treatmentSale) {
        try {
            return Result.success(
                    treatmentSaleMapper.toDomain(
                            treatmentSaleHibernateRepository.save(treatmentSaleMapper.toEntity(treatmentSale))));
        } catch (ConstraintViolationException e) {
            return Result.failure(TreatmentSaleFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(TreatmentSaleFailure.UNEXPECTED);

            log.error("Error saving treatment sale: {}", treatmentSale, e);

            return Result.failure(TreatmentSaleFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(TreatmentSaleFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(TreatmentSaleFailure.UNEXPECTED);

            log.error("Error saving treatment sale: {}", treatmentSale, e);

            return Result.failure(TreatmentSaleFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, TreatmentSaleFailure> deleteById(Long id) {
        try {
            treatmentSaleHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Treatment sale with id {} not found", id);

            return Result.failure(TreatmentSaleFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting treatment sale with id: {}", id, e);

            return Result.failure(TreatmentSaleFailure.UNEXPECTED);
        }
    }
}
