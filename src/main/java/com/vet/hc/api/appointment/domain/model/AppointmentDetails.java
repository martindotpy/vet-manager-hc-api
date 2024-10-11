package com.vet.hc.api.appointment.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents the details of an appointment.
 */
@Getter
@Builder
public class AppointmentDetails {
    private Long id;

    private Integer durationInMinutes;
    private Double price;
    private Appointment appointment;
}
