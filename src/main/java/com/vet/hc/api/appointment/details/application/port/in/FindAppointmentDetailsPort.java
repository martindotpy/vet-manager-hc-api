package com.vet.hc.api.appointment.details.application.port.in;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;

/**
 * Port for finding the appointment details.
 */
public interface FindAppointmentDetailsPort {
    /**
     * Find the appointment details by id.
     *
     * @param id the appointment details id.
     * @return the appointment details
     */
    Optional<AppointmentDetails> findById(Long id);

    /**
     * Find all appointment details.
     *
     * @return the list of appointment details
     */
    List<AppointmentDetails> findAll();
}
