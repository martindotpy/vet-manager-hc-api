package com.vluepixel.vetmanager.api.appointment.details.adapter.in.response;

import com.vluepixel.vetmanager.api.appointment.details.application.dto.AppointmentDetailsDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated appointment details response.
 */
@SuperBuilder
public final class PaginatedAppointmentDetailsResponse extends PaginatedResponse<AppointmentDetailsDto> {
}
