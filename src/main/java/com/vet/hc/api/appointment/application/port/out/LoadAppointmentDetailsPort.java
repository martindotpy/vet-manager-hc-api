package com.vet.hc.api.appointment.application.port.out;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.domain.model.AppointmentDetails;

/**
 * Port for load the appointment details.
 */
public interface LoadAppointmentDetailsPort {
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
