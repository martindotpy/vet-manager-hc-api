package com.vet.hc.api.bill.appointmentsale.domain.dto;

import com.vet.hc.api.appointment.core.domain.dto.AppointmentDto;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for appointment sale.
 *
 * @see com.vet.hc.api.bill.appointmentsale.domain.model.AppointmentSale
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentSaleDto {
    private Long id;

    private Double price;
    private Integer discount;

    private AppointmentDto appointment;
    private UserDto seller;
}
