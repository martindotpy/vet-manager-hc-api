package com.vet.hc.api.patient.race.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.adapter.out.persistence.repository.RaceHibernateRepository;
import com.vet.hc.api.patient.race.domain.failure.RaceFailure;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist races in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class RacePersistenceAdapter implements RaceRepository {
    private final RaceHibernateRepository raceHibernateRepository;
    private final RaceMapper raceMapper;

    @Override
    public List<Race> findAll() {
        return raceHibernateRepository.findAll().stream()
                .map(raceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Race> findById(Long id) {
        return raceHibernateRepository.findById(id)
                .map(raceMapper::toDomain);
    }

    @Override
    public Result<Race, RaceFailure> save(Race race) {
        try {
            return Result.success(raceMapper
                    .toDomain(
                            raceHibernateRepository.save(raceMapper.toEntity(race))));
        } catch (ConstraintViolationException e) {
            return Result.failure(RaceFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(RaceFailure.UNEXPECTED);

            log.error("Error saving race : {}", race, e);

            return Result.failure(RaceFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(RaceFailure.UNEXPECTED);

            log.error("Error saving race : {}", race, e);

            return Result.failure(RaceFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RaceFailure> deleteById(Long id) {
        try {
            raceHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Race  with id {} not found", id);

            return Result.failure(RaceFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting race with id: {}", id, e);

            return Result.failure(RaceFailure.UNEXPECTED);
        }
    }
}
