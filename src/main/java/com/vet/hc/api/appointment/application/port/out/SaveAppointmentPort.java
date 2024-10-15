package com.vet.hc.api.appointment.application.port.out;

import com.vet.hc.api.appointment.domain.model.Appointment;

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
