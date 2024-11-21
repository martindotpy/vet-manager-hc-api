package com.vet.hc.api.appointment.core.application.port.in;

import com.vet.hc.api.appointment.core.domain.model.Appointment;

/**
 * Port for save the appointment.
 */
public interface SaveAppointmentPort {
    /**
     * Save the appointment.
     *
     * @param appointment the appointment to save.
     * @return the saved appointment
     */
    Appointment save(Appointment appointment);
}
