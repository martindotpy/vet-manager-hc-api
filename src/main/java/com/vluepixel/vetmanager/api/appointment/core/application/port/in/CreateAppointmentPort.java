package com.vluepixel.vetmanager.api.appointment.core.application.port.in;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.appointment.core.domain.request.CreateAppointmentRequest;

/**
 * Create appointment port.
 */
public interface CreateAppointmentPort {
    /**
     * Create appointment.
     *
     * @param request the create appointment request.
     * @return the created appointment
     */
    AppointmentDto create(CreateAppointmentRequest request);
}
