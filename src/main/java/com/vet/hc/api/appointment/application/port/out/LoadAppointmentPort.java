package com.vet.hc.api.appointment.application.port.out;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.domain.model.Appointment;

/**
 * Port for load the appointment.
 */
public interface LoadAppointmentPort {
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
