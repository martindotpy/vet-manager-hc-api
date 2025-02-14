package com.vluepixel.vetmanager.api.patient.core.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.patient.core.domain.model.Patient;

/**
 * Spring Data JPA repository for {@link Patient}.
 */
public interface PatientSpringRepository extends JpaRepository<Patient, Long> {
}
