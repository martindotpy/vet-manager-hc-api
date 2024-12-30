package com.vet.hc.api.patient.vaccine.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;
import com.vet.hc.api.patient.vaccine.adapter.out.persistence.repository.VaccineHibernateRepository;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.patient.vaccine.domain.repository.VaccineRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist vaccines in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class VaccinePersistenceAdapter implements VaccineRepository {
    private final VaccineHibernateRepository vaccineHibernateRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    public Optional<Vaccine> findById(Long id) {
        return vaccineHibernateRepository.findById(id)
                .map(vaccineMapper::toDomain);
    }

    @Override
    public Result<Vaccine, VaccineFailure> save(Vaccine vaccine) {
        try {
            return Result.success(
                    vaccineMapper.toDomain(vaccineHibernateRepository.save(vaccineMapper.toEntity(vaccine))));
        } catch (ConstraintViolationException e) {
            return Result.failure(VaccineFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(VaccineFailure.UNEXPECTED);

            log.error("Error saving vaccine: {}", vaccine, e);

            return Result.failure(VaccineFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(VaccineFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(VaccineFailure.UNEXPECTED);

            log.error("Error saving vaccine: {}", vaccine, e);

            return Result.failure(VaccineFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, VaccineFailure> deleteById(Long id) {
        try {
            vaccineHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Vaccine with id {} not found", id);

            return Result.failure(VaccineFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting vaccine with id: {}", id, e);

            return Result.failure(VaccineFailure.UNEXPECTED);
        }
    }
}
