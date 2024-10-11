package com.vet.hc.api.appointment.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an appointment type.
 *
 * <p>
 * An appointment type is a type of appointment that can be scheduled, the data
 * will be changed by the vet when the appointment is created.
 * </p>
 */
@Getter
@Builder
public class AppointmentType {
    private Long id;

    private String name;
    private Integer durationInMinutes;
    private Double price;
}
