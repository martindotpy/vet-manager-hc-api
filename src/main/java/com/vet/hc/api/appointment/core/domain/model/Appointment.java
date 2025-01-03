package com.vet.hc.api.appointment.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.appointment.details.domain.model.AppointmentDetails;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.user.core.domain.model.UserImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an appointment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private String description;

    private List<AppointmentDetails> details;
    private Patient patient;
    private UserImpl vet;
}
