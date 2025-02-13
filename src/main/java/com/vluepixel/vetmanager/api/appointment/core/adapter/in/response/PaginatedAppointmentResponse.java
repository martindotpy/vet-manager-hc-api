package com.vluepixel.vetmanager.api.appointment.core.adapter.in.response;

import com.vluepixel.vetmanager.api.appointment.core.application.dto.AppointmentDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated appointment response.
 */
@SuperBuilder
public final class PaginatedAppointmentResponse extends PaginatedResponse<AppointmentDto> {
}
