package com.vluepixel.vetmanager.api.appointment.type.application.port.in;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.UpdateAppointmentTypeRequest;

/**
 * Update appointment type port.
 */
public interface UpdateAppointmentTypePort {
    /**
     * Update appointment type.
     *
     * @param request the update appointment type request.
     * @return the updated appointment type
     */
    AppointmentTypeDto update(UpdateAppointmentTypeRequest request);
}
