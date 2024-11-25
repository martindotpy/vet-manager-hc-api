package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.appointmentsale.domain.payload.CreateAppointmentSalePayload;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add appointment to bill port.
 */
public interface AddAppointmentSaleToBillPort {
    /**
     * Add a appointment to a bill.
     *
     * @param payload The payload with the data to create the appointment.
     * @return The result of the operation
     */
    Result<BillDto, BillFailure> add(CreateAppointmentSalePayload payload);
}
