package com.vet.hc.api.appointment.core.application.port.in;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.core.domain.model.Appointment;

/**
 * Port for finding the appointment.
 */
public interface FindAppointmentPort {
    /**
     * Find the appointment by id.
     *
     * @param id the appointment id.
     * @return the appointment
     */
    Optional<Appointment> findById(Long id);

    /**
     * Find all appointments.
     *
     * @return the list of appointments
     */
    List<Appointment> findAll();
}
