package com.vluepixel.vetmanager.api.appointment.type.adapter.in.response;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;
import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated appointment type response.
 */
@SuperBuilder
public final class PaginatedAppointmentTypeResponse extends PaginatedResponse<AppointmentTypeDto> {
}
