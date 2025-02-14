package com.vluepixel.vetmanager.api.medicalrecord.core.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;

/**
 * Spring Data JPA repository for {@link MedicalRecord}.
 */
public interface MedicalRecordSpringRepository extends JpaRepository<MedicalRecord, Long> {
}
