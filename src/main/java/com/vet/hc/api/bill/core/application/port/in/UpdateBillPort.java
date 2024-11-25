package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.payload.UpdateBillPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a bill.
 */
public interface UpdateBillPort {
    /**
     * Update a bill.
     *
     * @param payload the payload to update a bill.
     * @return the updated bill
     */
    Result<BillDto, BillFailure> update(UpdateBillPayload payload);
}
