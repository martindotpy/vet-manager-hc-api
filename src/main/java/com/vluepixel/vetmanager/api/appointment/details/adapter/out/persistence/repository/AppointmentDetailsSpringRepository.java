package com.vluepixel.vetmanager.api.appointment.details.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;

/**
 * Spring Data JPA repository for {@link AppointmentDetails}.
 */
public interface AppointmentDetailsSpringRepository extends JpaRepository<AppointmentDetails, Long> {
}
