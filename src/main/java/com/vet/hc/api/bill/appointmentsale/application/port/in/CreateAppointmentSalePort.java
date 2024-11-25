package com.vet.hc.api.bill.appointmentsale.application.port.in;

import com.vet.hc.api.bill.appointmentsale.domain.dto.AppointmentSaleDto;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.payload.CreateAppointmentSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a appointment sale.
 */
public interface CreateAppointmentSalePort {
    /**
     * Create a appointment sale.
     *
     * @param payload Payload with the data to create the appointment sale.
     * @return Result with the appointment sale created or the failure
     */
    Result<AppointmentSaleDto, AppointmentSaleFailure> create(CreateAppointmentSalePayload payload);
}
