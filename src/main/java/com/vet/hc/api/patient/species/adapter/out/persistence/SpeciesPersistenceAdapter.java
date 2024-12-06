package com.vet.hc.api.patient.species.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.species.adapter.out.mapper.SpeciesMapper;
import com.vet.hc.api.patient.species.adapter.out.persistence.repository.SpeciesHibernateRepository;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist species in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class SpeciesPersistenceAdapter implements SpeciesRepository {
    private final SpeciesHibernateRepository speciesHibernateRepository;
    private final SpeciesMapper speciesMapper;

    @Override
    public List<Species> findAll() {
        return speciesHibernateRepository.findAll().stream()
                .map(speciesMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Species> findById(Long id) {
        return speciesHibernateRepository.findById(id)
                .map(speciesMapper::toDomain);
    }

    @Override
    public Result<Species, RepositoryFailure> save(Species species) {
        try {
            return Result.success(speciesMapper
                    .toDomain(
                            speciesHibernateRepository.save(speciesMapper.toEntity(species))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, species));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, species));

            log.error("Error saving species : {}", species, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, species));

            log.error("Error saving species : {}", species, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            speciesHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Species  with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting species with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e       The exception.
     * @param species The species.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            Species species) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        if (mySqlFailure == MySQLRepositoryFailure.DUPLICATED) {
            RepositoryFailure repositoryFailure = RepositoryFailure.DUPLICATED;

            if (e.getConstraintName().equals("UK_SPECIE_NAME")) {
                log.error("The species with name `{}` already exists", species.getName());

                repositoryFailure.setField("name");
            } else {
                log.error("Error saving species : {}", species, e);
                repositoryFailure = RepositoryFailure.UNEXPECTED;
            }

            return repositoryFailure;
        }

        log.error("Error saving species with name `{}`", species.getName(), e);

        return RepositoryFailure.UNEXPECTED;
    }
}
