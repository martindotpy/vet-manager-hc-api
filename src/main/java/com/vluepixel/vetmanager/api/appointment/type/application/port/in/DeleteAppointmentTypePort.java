package com.vluepixel.vetmanager.api.appointment.type.application.port.in;

/**
 * Delete appointment type port.
 */
public interface DeleteAppointmentTypePort {
    /**
     * Delete appointment type by id.
     *
     * @param id the appointment type id
     */
    void deleteById(Integer id);
}
