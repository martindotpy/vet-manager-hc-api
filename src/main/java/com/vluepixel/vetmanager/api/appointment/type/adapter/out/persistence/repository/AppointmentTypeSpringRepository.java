package com.vluepixel.vetmanager.api.appointment.type.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.appointment.type.domain.model.AppointmentType;

/**
 * Spring Data JPA repository for {@link AppointmentType}.
 */
public interface AppointmentTypeSpringRepository extends JpaRepository<AppointmentType, Integer> {
}
