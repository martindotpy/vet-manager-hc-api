package com.vet.hc.api.appointment.type.application.port.in;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.type.domain.model.AppointmentType;

/**
 * Port for finding the appointment type.
 */
public interface FindAppointmentTypePort {
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
