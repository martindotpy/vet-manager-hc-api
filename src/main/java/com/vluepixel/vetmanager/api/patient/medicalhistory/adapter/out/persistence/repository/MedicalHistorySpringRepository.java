package com.vluepixel.vetmanager.api.patient.medicalhistory.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.patient.medicalhistory.domain.model.MedicalHistory;

/**
 * Spring Data JPA repository for {@link MedicalHistory}.
 */
public interface MedicalHistorySpringRepository extends JpaRepository<MedicalHistory, Long> {
}
