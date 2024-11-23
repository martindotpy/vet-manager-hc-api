package com.vet.hc.api.appointment.details.domain.dto;

import com.vet.hc.api.appointment.type.domain.dto.AppointmentTypeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for appointment details.
 *
 * @see com.vet.hc.api.appointment.details.domain.model.AppointmentDetails
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDetailsDto {
    private Long id;

    private Long durationInMinutes;
    private Double price;

    private AppointmentTypeDto type;
}
