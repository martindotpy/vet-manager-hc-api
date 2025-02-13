package com.vluepixel.vetmanager.api.appointment.details.domain.repository;

import com.vluepixel.vetmanager.api.appointment.details.domain.model.AppointmentDetails;
import com.vluepixel.vetmanager.api.shared.domain.repository.CriteriaRepository;

/**
 * Appointment details repository.
 */
public interface AppointmentDetailsRepository extends CriteriaRepository<AppointmentDetails, Long> {
}
