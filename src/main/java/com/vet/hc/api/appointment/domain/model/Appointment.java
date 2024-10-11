package com.vet.hc.api.appointment.domain.model;

import java.time.Instant;

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

    // Bill
    // Appointment details
    // Patient
    // Vet
}
