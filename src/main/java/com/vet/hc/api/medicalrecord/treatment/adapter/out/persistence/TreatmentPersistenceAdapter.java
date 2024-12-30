package com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.mapper.TreatmentMapper;
import com.vet.hc.api.medicalrecord.treatment.adapter.out.persistence.repository.TreatmentHibernateRepository;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vet.hc.api.shared.domain.query.Result;

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

    @Override
    public Optional<Treatment> findById(Long id) {
        return treatmentHibernateRepository.findById(id)
                .map(treatmentMapper::toDomain);
    }

    @Override
    public Result<Treatment, TreatmentFailure> save(Treatment treatment) {
        try {
            return Result.success(
                    treatmentMapper.toDomain(
                            treatmentHibernateRepository.save(treatmentMapper.toEntity(treatment))));
        } catch (ConstraintViolationException e) {
            return Result.failure(TreatmentFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(TreatmentFailure.UNEXPECTED);

            log.error("Error saving treatment: {}", treatment, e);

            return Result.failure(TreatmentFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(TreatmentFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(TreatmentFailure.UNEXPECTED);

            log.error("Error saving treatment: {}", treatment, e);

            return Result.failure(TreatmentFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, TreatmentFailure> deleteById(Long id) {
        try {
            treatmentHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Treatment with id {} not found", id);

            return Result.failure(TreatmentFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting treatment with id: {}", id, e);

            return Result.failure(TreatmentFailure.UNEXPECTED);
        }
    }
}
