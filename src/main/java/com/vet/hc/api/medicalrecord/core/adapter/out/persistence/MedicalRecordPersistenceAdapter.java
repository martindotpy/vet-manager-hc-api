package com.vet.hc.api.medicalrecord.core.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.medicalrecord.core.adapter.out.mapper.MedicalRecordMapper;
import com.vet.hc.api.medicalrecord.core.adapter.out.persistence.repository.MedicalRecordHibernateRepository;
import com.vet.hc.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vet.hc.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

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
    private final RepositoryFailureMapper repositoryFailureMapper;

    @Override
    public Optional<MedicalRecord> findById(Long id) {
        return medicalRecordHibernateRepository.findById(id)
                .map(medicalRecordMapper::toDomain);
    }

    @Override
    public Result<MedicalRecord, RepositoryFailure> save(MedicalRecord medicalRecord) {
        try {
            return Result.success(
                    medicalRecordMapper.toDomain(
                            medicalRecordHibernateRepository.save(medicalRecordMapper.toEntity(medicalRecord))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, medicalRecord));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, medicalRecord));

            log.error("Error saving medicalRecord: {}", medicalRecord, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, medicalRecord));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, medicalRecord));

            log.error("Error saving medicalRecord: {}", medicalRecord, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            medicalRecordHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("MedicalRecord with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting medical record with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e       The exception.
     * @param medical record The medical record.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            MedicalRecord medicalRecord) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving medical record with id `{}`", medicalRecord.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e       The exception.
     * @param medical record The medical record.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            MedicalRecord medicalRecord) {
        if (e.getMessage().contains("AppointmentTypeEntity")) {
            log.error("MedicalRecord with id `{}` not found", medicalRecord.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("AppointmentEntity")) {
            log.error("MedicalRecord with id `{}` not found", medicalRecord.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("appointment");

            return repositoryFailure;
        }

        log.error("Error saving medicalRecord", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
