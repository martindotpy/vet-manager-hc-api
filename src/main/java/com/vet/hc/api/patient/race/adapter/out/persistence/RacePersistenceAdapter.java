package com.vet.hc.api.patient.race.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.patient.race.adapter.out.mapper.RaceMapper;
import com.vet.hc.api.patient.race.adapter.out.persistence.repository.RaceHibernateRepository;
import com.vet.hc.api.patient.race.domain.model.Race;
import com.vet.hc.api.patient.race.domain.repository.RaceRepository;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist races in the database.
 */
@Slf4j
@NoArgsConstructor
public final class RacePersistenceAdapter implements RaceRepository {
    private RaceHibernateRepository raceHibernateRepository;

    private final RaceMapper raceMapper = RaceMapper.INSTANCE;

    @Inject
    public RacePersistenceAdapter(RaceHibernateRepository raceHibernateRepository) {
        this.raceHibernateRepository = raceHibernateRepository;
    }

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
    public Result<Race, RepositoryFailure> save(Race race) {
        try {
            return Result.success(raceMapper
                    .toDomain(
                            raceHibernateRepository.save(raceMapper.toEntity(race))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, race));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, race));

            log.error("Error saving race : {}", race, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, race));

            log.error("Error saving race : {}", race, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            raceHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Race  with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting race with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e    The exception.
     * @param race The race.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            Race race) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        if (mySqlFailure == MySQLRepositoryFailure.DUPLICATED) {
            RepositoryFailure repositoryFailure = RepositoryFailure.DUPLICATED;

            if (e.getConstraintName().equals("UK_SPECIE_NAME")) {
                log.error("The race with name `{}` already exists", race.getName());

                repositoryFailure.setField("name");
            } else {
                log.error("Error saving race : {}", race, e);
                repositoryFailure = RepositoryFailure.UNEXPECTED;
            }

            return repositoryFailure;
        }

        log.error("Error saving race with name `{}`", race.getName(), e);

        return RepositoryFailure.UNEXPECTED;
    }
}
