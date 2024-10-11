package com.vet.hc.api.appointment.domain.model;

import java.time.Instant;

import com.vet.hc.api.patient.domain.model.Patient;
import com.vet.hc.api.sales.domain.model.Bill;
import com.vet.hc.api.vet.domain.model.Vet;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an appointment.
 */
@Getter
@Builder
public class Appointment {
    private Long id;

    private Instant createdAt;
    private Instant startAt;
    private String description;

    private Bill bill;
    private AppointmentDetails appointmentDetails;
    private Patient patient;
    private Vet vet;
}
