package com.vet.hc.api.bill.treatmentsale.application.port.in;

import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.payload.UpdateTreatmentSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a treatment sale.
 */
public interface UpdateTreatmentSalePort {
    /**
     * Update a treatment sale.
     *
     * @param payload Payload with the data to update the treatment sale.
     * @return Result with the treatment sale updated or the failure
     */
    Result<TreatmentSaleDto, TreatmentSaleFailure> update(UpdateTreatmentSalePayload payload);
}
