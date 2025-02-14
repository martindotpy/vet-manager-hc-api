package com.vluepixel.vetmanager.api.patient.species.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.patient.species.domain.model.Species;

/**
 * Spring Data JPA repository for {@link Species}.
 */
public interface SpeciesSpringRepository extends JpaRepository<Species, Integer> {
}
