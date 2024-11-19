package com.vet.hc.api.appointment.application.port.out;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.appointment.domain.enums.AppointmentType;

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
