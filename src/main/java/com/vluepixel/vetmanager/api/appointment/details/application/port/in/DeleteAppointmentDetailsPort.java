package com.vluepixel.vetmanager.api.appointment.details.application.port.in;

/**
 * Delete appointment details port.
 */
public interface DeleteAppointmentDetailsPort {
    /**
     * Delete appointment details by id.
     *
     * @param id the appointment details id.
     */
    void deleteById(Long id);
}
