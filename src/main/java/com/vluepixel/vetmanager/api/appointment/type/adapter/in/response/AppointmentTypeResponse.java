package com.vluepixel.vetmanager.api.appointment.type.adapter.in.response;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Appointment type response.
 */
@SuperBuilder
public final class AppointmentTypeResponse extends ContentResponse<AppointmentTypeDto> {
}
