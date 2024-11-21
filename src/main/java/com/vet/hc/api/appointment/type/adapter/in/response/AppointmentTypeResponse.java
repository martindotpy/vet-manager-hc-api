package com.vet.hc.api.appointment.type.adapter.in.response;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link AppointmentTypeDto}.
 */
@SuperBuilder
public final class AppointmentTypeResponse extends ContentResponse<AppointmentTypeDto> {
}
