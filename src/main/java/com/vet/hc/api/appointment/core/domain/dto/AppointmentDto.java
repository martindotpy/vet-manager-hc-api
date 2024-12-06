package com.vet.hc.api.appointment.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.appointment.details.domain.dto.AppointmentDetailsDto;
import com.vet.hc.api.patient.core.domain.dto.PatientDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the appointment.
 */
@ColumnClassName("Cita")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Fecha de creación")
    private LocalDateTime createdAt;
    @ColumnPropertyName("Fecha de inicio")
    private LocalDateTime startAt;
    @ColumnPropertyName("Descripción")
    private String description;

    @ColumnPropertyName("Detalles")
    private List<AppointmentDetailsDto> details;
    @ColumnPropertyName("Paciente")
    private PatientDto patient;
    @ColumnPropertyName("Veterinario")
    private UserDto vet;
}
