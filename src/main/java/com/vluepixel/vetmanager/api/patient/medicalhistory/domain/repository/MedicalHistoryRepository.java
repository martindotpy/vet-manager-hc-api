package com.vluepixel.vetmanager.api.patient.medicalhistory.domain.repository;

import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;
import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;

/**
 * Medical history repository.
 */
public interface MedicalHistoryRepository extends CriteriaRepository<MedicalHistory, Long> {
}
