package com.vet.hc.api.appointment.core.domain.dto;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDto {
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private String description;

    // private BillDto bill;
    // private AppointmentDetailsDto details;
    private PatientDto patient;
    private UserDto vet;
}
