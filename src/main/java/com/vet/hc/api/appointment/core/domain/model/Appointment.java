package com.vet.hc.api.appointment.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an appointment.
 */
@ColumnClassName("Cita")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Fecha de creación")
    private LocalDateTime createdAt;
    @ColumnPropertyName("Fecha de inicio")
    private LocalDateTime startAt;
    @ColumnPropertyName("Descripción")
    private String description;

    @ColumnPropertyName("Detalles")
    private List<AppointmentDetails> details;
    @ColumnPropertyName("Paciente")
    private Patient patient;
    @ColumnPropertyName("Veterinario")
    private User vet;
}
