package com.vet.hc.api.appointment.details.domain.model;

import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.appointment.type.domain.model.AppointmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the details of an appointment.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetails {
    private Long id;

    private Integer durationInMinutes;
    private Double price;

    private AppointmentType type;
    private Appointment appointment;
}
