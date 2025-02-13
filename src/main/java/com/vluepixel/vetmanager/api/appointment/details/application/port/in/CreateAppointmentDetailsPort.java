package com.vluepixel.vetmanager.api.appointment.details.application.port.in;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.appointment.details.domain.request.CreateAppointmentDetailsRequest;

/**
 * Create appointment details port.
 */
public interface CreateAppointmentDetailsPort {
    /**
     * Create appointment details.
     *
     * @param request the create appointment details request.
     * @return the created appointment details
     */
    AppointmentDetailsDto create(CreateAppointmentDetailsRequest request);
}
