package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.payload.CreateBillPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for creating a bill.
 */
public interface CreateBillPort {
    Result<BillDto, BillFailure> create(CreateBillPayload payload);
}
