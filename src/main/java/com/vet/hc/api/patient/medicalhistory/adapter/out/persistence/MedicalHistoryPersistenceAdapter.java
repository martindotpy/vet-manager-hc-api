package com.vet.hc.api.patient.medicalhistory.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.medicalhistory.adapter.out.mapper.MedicalHistoryMapper;
import com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.repository.MedicalHistoryHibernateRepository;
import com.vet.hc.api.patient.medicalhistory.domain.failure.MedicalHistoryFailure;
import com.vet.hc.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vet.hc.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vet.hc.api.shared.domain.query.Result;

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

    @Override
    public Optional<MedicalHistory> findById(Long id) {
        return medicalHistoryHibernateRepository.findById(id)
                .map(medicalHistoryMapper::toDomain);
    }

    @Override
    public Result<MedicalHistory, MedicalHistoryFailure> save(MedicalHistory medicalHistory) {
        try {
            return Result.success(
                    medicalHistoryMapper.toDomain(
                            medicalHistoryHibernateRepository.save(medicalHistoryMapper.toEntity(medicalHistory))));
        } catch (ConstraintViolationException e) {
            return Result.failure(MedicalHistoryFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(MedicalHistoryFailure.UNEXPECTED);

            log.error("Error saving medicalHistory: {}", medicalHistory, e);

            return Result.failure(MedicalHistoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(MedicalHistoryFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(MedicalHistoryFailure.UNEXPECTED);

            log.error("Error saving medicalHistory: {}", medicalHistory, e);

            return Result.failure(MedicalHistoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, MedicalHistoryFailure> deleteById(Long id) {
        try {
            medicalHistoryHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("MedicalHistory with id {} not found", id);

            return Result.failure(MedicalHistoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting medical history with id: {}", id, e);

            return Result.failure(MedicalHistoryFailure.UNEXPECTED);
        }
    }
}
