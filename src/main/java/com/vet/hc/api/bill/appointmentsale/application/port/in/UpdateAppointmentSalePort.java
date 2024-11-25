package com.vet.hc.api.bill.appointmentsale.application.port.in;

import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.payload.UpdateAppointmentSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a appointment sale.
 */
public interface UpdateAppointmentSalePort {
    /**
     * Update a appointment sale.
     *
     * @param payload Payload with the data to update the appointment sale.
     * @return Result with the appointment sale updated or the failure
     */
    Result<AppointmentSaleDto, AppointmentSaleFailure> update(UpdateAppointmentSalePayload payload);
}
