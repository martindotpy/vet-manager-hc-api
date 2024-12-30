package com.vet.hc.api.patient.species.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.patient.species.adapter.out.mapper.SpeciesMapper;
import com.vet.hc.api.patient.species.adapter.out.persistence.repository.SpeciesHibernateRepository;
import com.vet.hc.api.patient.species.domain.failure.SpeciesFailure;
import com.vet.hc.api.patient.species.domain.model.Species;
import com.vet.hc.api.patient.species.domain.repository.SpeciesRepository;
import com.vet.hc.api.shared.domain.query.Result;

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
    public Result<Species, SpeciesFailure> save(Species species) {
        try {
            return Result.success(speciesMapper
                    .toDomain(
                            speciesHibernateRepository.save(speciesMapper.toEntity(species))));
        } catch (ConstraintViolationException e) {
            return Result.failure(SpeciesFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(SpeciesFailure.UNEXPECTED);

            log.error("Error saving species : {}", species, e);

            return Result.failure(SpeciesFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(SpeciesFailure.UNEXPECTED);

            log.error("Error saving species : {}", species, e);

            return Result.failure(SpeciesFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, SpeciesFailure> deleteById(Long id) {
        try {
            speciesHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Species  with id {} not found", id);

            return Result.failure(SpeciesFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting species with id: {}", id, e);

            return Result.failure(SpeciesFailure.UNEXPECTED);
        }
    }
}
