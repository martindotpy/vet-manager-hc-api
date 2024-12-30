package com.vet.hc.api.bill.appointmentsale.domain.model;

import com.vet.hc.api.appointment.core.domain.model.Appointment;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.user.core.domain.model.UserImpl;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a appointment sale.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSale {
    private Long id;

    private Double price;
    private Integer discount;

    private Appointment appointment;
    private UserImpl seller;
    @Nullable
    private Bill bill;
}
