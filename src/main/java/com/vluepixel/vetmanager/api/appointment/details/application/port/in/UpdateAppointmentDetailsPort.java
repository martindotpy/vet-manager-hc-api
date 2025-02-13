package com.vluepixel.vetmanager.api.appointment.details.application.port.in;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.UpdateAppointmentDetailsRequest;

/**
 * Update appointment details port.
 */
public interface UpdateAppointmentDetailsPort {
    /**
     * Update appointment details.
     *
     * @param request the update appointment details request.
     * @return the updated appointment details
     */
    AppointmentDetailsDto update(UpdateAppointmentDetailsRequest request);
}
