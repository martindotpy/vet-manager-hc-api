package com.vluepixel.vetmanager.api.medicalrecord.core.adapter.out.persistence;

import com.vluepixel.vetmanager.api.medicalrecord.core.adapter.out.persistence.repository.MedicalRecordSpringRepository;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;

/**
 * Persistence adapter for {@link MedicalRecord}.
 */
@PersistenceAdapter
public class MedicalRecordPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<MedicalRecord, Long, MedicalRecordSpringRepository>
        implements MedicalRecordRepository {
    public MedicalRecordPersistenceAdapter(MedicalRecordSpringRepository repository) {
        super(repository);
    }
}
