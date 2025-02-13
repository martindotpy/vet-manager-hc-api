package com.vluepixel.vetmanager.api.appointment.core.adapter.in.response;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Appointment response.
 */
@SuperBuilder
public final class AppointmentResponse extends ContentResponse<AppointmentDto> {
}
