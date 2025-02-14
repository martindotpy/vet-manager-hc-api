package com.vluepixel.vetmanager.api.vaccine.core.adapter.out.persistence;

import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;
import com.vluepixel.vetmanager.api.vaccine.core.adapter.out.persistence.repository.VaccineSpringRepository;
import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;
import com.vluepixel.vetmanager.api.vaccine.core.domain.repository.VaccineRepository;

/**
 * Persistence adapter for {@link Vaccine}.
 */
@PersistenceAdapter
public class VaccinePersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Vaccine, Long, VaccineSpringRepository>
        implements VaccineRepository {
    public VaccinePersistenceAdapter(VaccineSpringRepository repository) {
        super(repository);
    }
}
