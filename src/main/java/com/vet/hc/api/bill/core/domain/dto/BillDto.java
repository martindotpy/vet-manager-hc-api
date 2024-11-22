package com.vet.hc.api.bill.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for appointment type.
 *
 * @see com.vet.hc.api.appointment.type.domain.model.Bill
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class BillDto {
    private Long id;

    private String name;
    private Long durationInMinutes;
    private Double price;
}
