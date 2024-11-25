package com.vet.hc.api.bill.core.domain.payload;

import com.vet.hc.api.shared.domain.payload.Payload;

public interface UpdateSalePayload extends Payload {
    Double getPrice();

    Integer getDiscount();
}
