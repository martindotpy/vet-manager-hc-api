package com.vet.hc.api.bill.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload to create a new appointment type.
 */
public interface CreateBillPayload extends Payload {
    String getName();

    Integer getDurationInMinutes();

    Double getPrice();
}
