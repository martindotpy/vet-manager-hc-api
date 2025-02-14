package com.vluepixel.vetmanager.api.medicalrecord.core.domain.repository;

import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;

/**
 * Medical record repository.
 */
public interface MedicalRecordRepository extends CriteriaRepository<MedicalRecord, Long> {
}
