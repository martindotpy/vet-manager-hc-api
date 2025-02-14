package com.vluepixel.vetmanager.api.vaccine.core.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.vaccine.core.domain.model.Vaccine;

/**
 * Spring Data JPA repository for {@link Vaccine}.
 */
public interface VaccineSpringRepository extends JpaRepository<Vaccine, Long> {
}
