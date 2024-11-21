package com.vet.hc.api.appointment.type.application.port.in;

import com.vet.hc.api.appointment.type.domain.model.AppointmentType;

/**
 * Port for save the appointment type.
 */
public interface SaveAppointmentTypePort {
    /**
     * Save the appointment type.
     *
     * @param appointmentType the appointment type to save.
     * @return the saved appointment type
     */
    AppointmentType save(AppointmentType appointmentType);
}
