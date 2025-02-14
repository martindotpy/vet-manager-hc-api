package com.vluepixel.vetmanager.api.patient.species.adapter.out.persistence;

import com.vluepixel.vetmanager.api.patient.species.adapter.out.persistence.repository.SpeciesSpringRepository;
import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;
import com.vluepixel.vetmanager.api.patient.species.domain.repository.SpeciesRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link Species}.
 */
@PersistenceAdapter
public class SpeciesPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Species, Integer, SpeciesSpringRepository>
        implements SpeciesRepository {
    public SpeciesPersistenceAdapter(SpeciesSpringRepository repository) {
        super(repository);
    }
}
