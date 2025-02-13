package com.vluepixel.vetmanager.api.appointment.core.application.port.in;

/**
 * Delete appointment port.
 */
public interface DeleteAppointmentPort {
    /**
     * Delete appointment by id.
     *
     * @param id the appointment id
     */
    void deleteById(Long id);
}
