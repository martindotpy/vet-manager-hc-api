package com.vluepixel.vetmanager.api.appointment.core.application.port.in;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.UpdateAppointmentRequest;

/**
 * Update appointment port.
 */
public interface UpdateAppointmentPort {
    /**
     * Update appointment.
     *
     * @param request the update appointment request.
     * @return the updated appointment
     */
    AppointmentDto update(UpdateAppointmentRequest request);
}
