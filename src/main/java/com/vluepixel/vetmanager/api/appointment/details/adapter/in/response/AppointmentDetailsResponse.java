package com.vluepixel.vetmanager.api.appointment.details.adapter.in.response;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Appointment details response.
 */
@SuperBuilder
public final class AppointmentDetailsResponse extends ContentResponse<AppointmentDetailsDto> {
}
