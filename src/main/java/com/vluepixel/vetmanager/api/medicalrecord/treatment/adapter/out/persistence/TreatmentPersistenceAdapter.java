package com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.out.persistence;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.out.persistence.repository.TreatmentSpringRepository;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.repository.TreatmentRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link Treatment}.
 */
@PersistenceAdapter
public class TreatmentPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Treatment, Long, TreatmentSpringRepository>
        implements TreatmentRepository {
    public TreatmentPersistenceAdapter(TreatmentSpringRepository repository) {
        super(repository);
    }
}
