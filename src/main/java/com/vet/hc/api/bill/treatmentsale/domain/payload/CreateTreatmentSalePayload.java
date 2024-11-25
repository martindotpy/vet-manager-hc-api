package com.vet.hc.api.bill.treatmentsale.domain.payload;

import com.vet.hc.api.bill.core.domain.payload.CreateSalePayload;

/**
 * Payload to create a new treatment sale.
 */
public interface CreateTreatmentSalePayload extends CreateSalePayload {
    Long getTreatmentId();
}
