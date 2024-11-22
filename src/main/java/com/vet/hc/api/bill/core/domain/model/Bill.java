package com.vet.hc.api.bill.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    private Long id;

    private String name;
    private Integer durationInMinutes;
    private Double price;
}
