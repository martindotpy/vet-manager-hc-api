package com.vluepixel.vetmanager.api.patient.race.adapter.out.persistence;

import com.vluepixel.vetmanager.api.patient.race.adapter.out.persistence.repository.RaceSpringRepository;
import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;
import com.vluepixel.vetmanager.api.patient.race.domain.repository.RaceRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link Race}.
 */
@PersistenceAdapter
public class RacePersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Race, Integer, RaceSpringRepository>
        implements RaceRepository {
    public RacePersistenceAdapter(RaceSpringRepository repository) {
        super(repository);
    }
}
