package com.vet.hc.api.appointment.type.application.port.in;

import java.util.List;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.appointment.type.domain.failure.AppointmentTypeFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the appointment type.
 */
public interface FindAppointmentTypePort {
    /**
     * Find all appointment types.
     *
     * @return the list of appointment types
     */
    List<AppointmentTypeDto> findAll();

    /**
     * Find the appointment type by id.
     *
     * @param id the appointment type id.
     * @return the appointment type found or the failure
     */
    Result<AppointmentTypeDto, AppointmentTypeFailure> findById(Long id);
}
