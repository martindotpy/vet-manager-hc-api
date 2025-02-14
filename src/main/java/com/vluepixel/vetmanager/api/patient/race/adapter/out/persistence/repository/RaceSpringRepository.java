package com.vluepixel.vetmanager.api.patient.race.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.patient.race.domain.model.Race;

/**
 * Spring Data JPA repository for {@link Race}.
 */
public interface RaceSpringRepository extends JpaRepository<Race, Integer> {
}
