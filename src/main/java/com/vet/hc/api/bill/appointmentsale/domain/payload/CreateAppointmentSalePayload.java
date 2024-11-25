package com.vet.hc.api.bill.appointmentsale.domain.payload;

import com.vet.hc.api.bill.core.domain.payload.CreateSalePayload;

/**
 * Payload to create a new appointment sale.
 */
public interface CreateAppointmentSalePayload extends CreateSalePayload {
    Long getAppointmentId();
}
