package com.vet.hc.api.bill.appointmentsale.application.port.in;

import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the appointment sale.
 */
public interface FindAppointmentSalePort {
    /**
     * Find the appointment sale by id.
     *
     * @param id the appointment sale id.
     * @return the appointment sale found or the failure
     */
    Result<AppointmentSaleDto, AppointmentSaleFailure> findById(Long id);
}
