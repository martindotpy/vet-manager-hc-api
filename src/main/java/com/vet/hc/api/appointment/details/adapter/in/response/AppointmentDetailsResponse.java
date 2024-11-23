package com.vet.hc.api.appointment.details.adapter.in.response;

import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link AppointmentDetailsDto}.
 */
@SuperBuilder
public final class AppointmentDetailsResponse extends ContentResponse<AppointmentDetailsDto> {
}
