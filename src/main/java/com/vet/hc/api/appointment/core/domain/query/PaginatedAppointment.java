package com.vet.hc.api.appointment.core.domain.query;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.shared.domain.query.Paginated;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedAppointment extends Paginated<AppointmentDto> {
}
