package com.vluepixel.vetmanager.api.appointment.type.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment type DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentTypeDto {
    private Long id;

    private String name;
    private int durationInMinutes;
    private BigDecimal price;
}
