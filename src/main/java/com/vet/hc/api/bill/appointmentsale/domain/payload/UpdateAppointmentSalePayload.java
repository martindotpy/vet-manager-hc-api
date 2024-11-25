package com.vet.hc.api.bill.appointmentsale.domain.payload;

import com.vet.hc.api.bill.core.domain.payload.UpdateSalePayload;

/**
 * Payload for updating a appointment sale.
 */
public interface UpdateAppointmentSalePayload extends UpdateSalePayload {
    Long getId();

    Long getAppointmentId();
}
