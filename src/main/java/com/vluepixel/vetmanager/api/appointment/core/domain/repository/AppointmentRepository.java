package com.vluepixel.vetmanager.api.appointment.core.domain.repository;

import com.vluepixel.vetmanager.api.appointment.core.domain.model.Appointment;
import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;

/**
 * Appointment repository.
 */
public interface AppointmentRepository extends CriteriaRepository<Appointment, Long> {
}
