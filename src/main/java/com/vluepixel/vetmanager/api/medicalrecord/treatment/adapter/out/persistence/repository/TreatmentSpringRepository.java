package com.vluepixel.vetmanager.api.medicalrecord.treatment.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.medicalrecord.treatment.domain.model.Treatment;

/**
 * Spring Data JPA repository for {@link Treatment}.
 */
public interface TreatmentSpringRepository extends JpaRepository<Treatment, Long> {
}
