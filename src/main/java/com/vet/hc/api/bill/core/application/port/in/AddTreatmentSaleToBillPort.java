package com.vet.hc.api.bill.core.application.port.in;

import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.treatmentsale.domain.payload.CreateTreatmentSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Add treatment to bill port.
 */
public interface AddTreatmentSaleToBillPort {
    /**
     * Add a treatment to a bill.
     *
     * @param payload The payload with the data to create the treatment.
     * @return The result of the operation
     */
    Result<BillDto, BillFailure> add(CreateTreatmentSalePayload payload);
}
