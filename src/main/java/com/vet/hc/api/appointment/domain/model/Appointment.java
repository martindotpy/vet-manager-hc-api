package com.vet.hc.api.appointment.domain.model;

import java.time.LocalDateTime;

import com.vet.hc.api.patient.domain.model.Patient;
import com.vet.hc.api.sales.domain.model.Bill;
import com.vet.hc.api.user.domain.model.User;

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

    private Bill bill;
    private AppointmentDetails appointmentDetails;
    private Patient patient;
    private User vet;
}
