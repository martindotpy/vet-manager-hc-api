package com.vluepixel.vetmanager.api.appointment.core.application.dto;

import java.time.LocalDateTime;

import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Appointment DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDto {
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private String description;
    // TODO: Patient
    private UserDto createdBy;
}
