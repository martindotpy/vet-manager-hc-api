package com.vet.hc.api.appointment.application.port.out;

import com.vet.hc.api.appointment.domain.enums.AppointmentType;

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
