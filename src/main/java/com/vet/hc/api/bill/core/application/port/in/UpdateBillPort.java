package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.payload.UpdateBillPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating an appointment type.
 */
public interface UpdateBillPort {
    /**
     * Update an appointment type.
     *
     * @param payload Payload with the data to update the appointment type.
     * @return Result with the appointment type updated or the failure
     */
    Result<BillDto, BillFailure> update(UpdateBillPayload payload);
}
