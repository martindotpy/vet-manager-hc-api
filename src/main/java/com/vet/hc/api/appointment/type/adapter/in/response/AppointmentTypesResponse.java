package com.vet.hc.api.appointment.type.adapter.in.response;

import java.util.List;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link AppointmentTypeDto} list.
 */
@SuperBuilder
public final class AppointmentTypesResponse extends ContentResponse<List<AppointmentTypeDto>> {
}
