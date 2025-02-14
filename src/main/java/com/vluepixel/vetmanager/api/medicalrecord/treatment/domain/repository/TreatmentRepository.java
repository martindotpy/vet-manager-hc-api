package com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.repository;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;
import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;

/**
 * Treatment repository.
 */
public interface TreatmentRepository extends CriteriaRepository<Treatment, Long> {
}
