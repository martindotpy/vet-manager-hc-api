package com.vluepixel.vetmanager.api.patient.core.adapter.out.persistence;

import com.vluepixel.vetmanager.api.patient.core.adapter.out.persistence.repository.PatientSpringRepository;
import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.patient.core.domain.repository.PatientRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link Patient}.
 */
@PersistenceAdapter
public class PatientPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<Patient, Long, PatientSpringRepository>
        implements PatientRepository {
    public PatientPersistenceAdapter(PatientSpringRepository repository) {
        super(repository);
    }
}
