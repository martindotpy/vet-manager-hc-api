package com.vluepixel.vetmanager.api.patient.core.domain.repository;

import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;
import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;

/**
 * Patient repository.
 */
public interface PatientRepository extends CriteriaRepository<Patient, Long> {
}
