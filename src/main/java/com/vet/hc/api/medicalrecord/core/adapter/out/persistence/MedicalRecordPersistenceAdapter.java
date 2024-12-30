package com.vet.hc.api.medicalrecord.core.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.medicalrecord.core.adapter.out.persistence.repository.MedicalRecordHibernateRepository;
import com.vet.hc.api.medicalrecord.core.domain.failure.MedicalRecordFailure;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist medicalRecords in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class MedicalRecordPersistenceAdapter implements MedicalRecordRepository {
    private final MedicalRecordHibernateRepository medicalRecordHibernateRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public Optional<MedicalRecord> findById(Long id) {
        return medicalRecordHibernateRepository.findById(id)
                .map(medicalRecordMapper::toDomain);
    }

    @Override
    public Result<MedicalRecord, MedicalRecordFailure> save(MedicalRecord medicalRecord) {
        try {
            return Result.success(
                    medicalRecordMapper.toDomain(
                            medicalRecordHibernateRepository.save(medicalRecordMapper.toEntity(medicalRecord))));
        } catch (ConstraintViolationException e) {
            return Result.failure(MedicalRecordFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(MedicalRecordFailure.UNEXPECTED);

            log.error("Error saving medicalRecord: {}", medicalRecord, e);

            return Result.failure(MedicalRecordFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(MedicalRecordFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(MedicalRecordFailure.UNEXPECTED);

            log.error("Error saving medicalRecord: {}", medicalRecord, e);

            return Result.failure(MedicalRecordFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, MedicalRecordFailure> deleteById(Long id) {
        try {
            medicalRecordHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("MedicalRecord with id {} not found", id);

            return Result.failure(MedicalRecordFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting medical record with id: {}", id, e);

            return Result.failure(MedicalRecordFailure.UNEXPECTED);
        }
    }
}
