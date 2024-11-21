package com.vet.hc.api.appointment.core.application.response;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class AppointmentResponse extends ContentResponse<AppointmentDto> {
}
