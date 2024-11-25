package com.vet.hc.api.bill.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Create bill payload.
 */
public interface CreateBillPayload extends Payload {
    Double getTotal();

    Integer getDiscount();

    Double getTotalPaid();

    Long getClientId();
}
