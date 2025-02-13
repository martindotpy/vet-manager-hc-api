package com.vluepixel.vetmanager.api.appointment.type.application.port.in;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.appointment.type.domain.request.CreateAppointmentTypeRequest;

/**
 * Create appointment type port.
 */
public interface CreateAppointmentTypePort {
    /**
     * Create appointment type.
     *
     * @param request the create appointment type request.
     * @return the created appointment type
     */
    AppointmentTypeDto create(CreateAppointmentTypeRequest request);
}
