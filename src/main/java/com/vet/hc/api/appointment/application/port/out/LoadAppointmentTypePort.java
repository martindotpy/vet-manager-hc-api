package com.vet.hc.api.appointment.application.port.out;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.domain.model.AppointmentType;

/**
 * Port for load the appointment type.
 */
public interface LoadAppointmentTypePort {
    /**
     * Find the appointment type by id.
     *
     * @param id the appointment type id.
     * @return the appointment type
     */
    Optional<AppointmentType> findById(Long id);

    /**
     * Find all appointment types.
     *
     * @return the list of appointment types
     */
    List<AppointmentType> findAll();
}
