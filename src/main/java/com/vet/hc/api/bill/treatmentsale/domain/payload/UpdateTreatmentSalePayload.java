package com.vet.hc.api.bill.treatmentsale.domain.payload;

import com.vet.hc.api.bill.core.domain.payload.UpdateSalePayload;

/**
 * Payload for updating a treatment sale.
 */
public interface UpdateTreatmentSalePayload extends UpdateSalePayload {
    Long getId();

    Long getTreatmentId();
}
