package com.vet.hc.api.bill.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Update bill payload.
 */
public interface UpdateBillPayload extends Payload {
    Long getId();

    Double getTotal();

    Integer getDiscount();

    Double getTotalPaid();
}
