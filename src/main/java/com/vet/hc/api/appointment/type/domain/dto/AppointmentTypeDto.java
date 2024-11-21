package com.vet.hc.api.appointment.type.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for appointment type.
 *
 * @see com.vet.hc.api.appointment.type.domain.model.AppointmentType
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentTypeDto {
    private Long id;

    private String name;
    private Long durationInMinutes;
    private Double price;
}
