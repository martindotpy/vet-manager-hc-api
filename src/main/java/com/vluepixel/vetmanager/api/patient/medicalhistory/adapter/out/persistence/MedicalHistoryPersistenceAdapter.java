package com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.out.persistence;

import com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.out.persistence.repository.MedicalHistorySpringRepository;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository.MedicalHistoryRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link MedicalHistory}.
 */
@PersistenceAdapter
public class MedicalHistoryPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<MedicalHistory, Long, MedicalHistorySpringRepository>
        implements MedicalHistoryRepository {
    public MedicalHistoryPersistenceAdapter(MedicalHistorySpringRepository repository) {
        super(repository);
    }
}
