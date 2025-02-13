package com.vluepixel.vetmanager.api.appointment.details.application.dto;

import java.math.BigDecimal;

import com.vluepixel.vetmanager.api.appointment.type.application.dto.AppointmentTypeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment details DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDetailsDto {
    private Long id;

    private int durationInMinutes;
    private BigDecimal price;

    private AppointmentTypeDto type;
}
