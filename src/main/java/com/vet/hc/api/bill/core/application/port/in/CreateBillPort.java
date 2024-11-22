package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.payload.CreateBillPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create an appointment type.
 */
public interface CreateBillPort {
    /**
     * Create an appointment type.
     *
     * @param payload Payload with the data to create the appointment type.
     * @return Result with the appointment type created or the failure
     */
    Result<BillDto, BillFailure> create(CreateBillPayload payload);
}
