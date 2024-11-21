package com.vet.hc.api.appointment.details.application.port.in;

import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;

/**
 * Port for save the appointment details.
 */
public interface SaveAppointmentDetailsPort {
    /**
     * Save the appointment details.
     *
     * @param appointmentDetails the appointment details to save.
     * @return the saved appointment details
     */
    AppointmentDetails save(AppointmentDetails appointmentDetails);
}
