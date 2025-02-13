package com.vluepixel.vetmanager.api.appointment.core.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.appointment.core.domain.model.Appointment;

/**
 * Spring Data JPA repository for {@link Appointment}.
 */
public interface AppointmentSpringRepository extends JpaRepository<Appointment, Long> {
}
