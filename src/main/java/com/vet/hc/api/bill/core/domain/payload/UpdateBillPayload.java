package com.vet.hc.api.bill.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

/**
 * Payload for updating an appointment type.
 */
public interface UpdateBillPayload extends Payload {
    Long getId();

    String getName();

    Integer getDurationInMinutes();

    Double getPrice();
}
