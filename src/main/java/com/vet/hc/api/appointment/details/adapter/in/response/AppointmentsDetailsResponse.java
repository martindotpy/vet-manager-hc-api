package com.vet.hc.api.appointment.details.adapter.in.response;

import java.util.List;

import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link AppointmentDetailsDto} list.
 */
@SuperBuilder
public final class AppointmentsDetailsResponse extends ContentResponse<List<AppointmentDetailsDto>> {
}
